package com.example.hardware

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import java.security.MessageDigest
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.roundToInt

data class ThrottlingPoint(
    val second: Int,
    val scoreGips: Double,
    val throttlePercent: Int,
    val temperatureC: Float
)

data class StressTestState(
    val isRunning: Boolean = false,
    val isFinished: Boolean = false,
    val elapsedSeconds: Int = 0,
    val totalSeconds: Int = 120, // 2 minutes stress test
    val currentGips: Double = 0.0,
    val peakGips: Double = 0.0,
    val avgGips: Double = 0.0,
    val currentThrottlePercent: Int = 100,
    val minThrottlePercent: Int = 100,
    val currentTempC: Float = 0f,
    val startTempC: Float = 0f,
    val peakTempC: Float = 0f,
    val points: List<ThrottlingPoint> = emptyList(),
    val verdictScore: Int = 100,
    val verdictRu: String = "",
    val verdictUa: String = "",
    val verdictEn: String = ""
)

class CpuThrottlingTester(private val context: Context) {

    private val _state = MutableStateFlow(StressTestState())
    val state: StateFlow<StressTestState> = _state.asStateFlow()

    private var testJob: Job? = null
    private val isComputing = AtomicBoolean(false)
    private val operationCounter = AtomicLong(0L)

    fun startTest(coroutineScope: CoroutineScope) {
        if (_state.value.isRunning) return

        testJob?.cancel()
        val initialTemp = readBatteryTemp()
        _state.value = StressTestState(
            isRunning = true,
            isFinished = false,
            elapsedSeconds = 0,
            totalSeconds = 120,
            startTempC = initialTemp,
            currentTempC = initialTemp,
            peakTempC = initialTemp,
            points = emptyList(),
            currentThrottlePercent = 100,
            minThrottlePercent = 100
        )

        isComputing.set(true)
        operationCounter.set(0L)

        // Spawn heavy math/crypto worker threads on all CPU cores
        val coreCount = Runtime.getRuntime().availableProcessors().coerceAtLeast(2)
        repeat(coreCount) {
            coroutineScope.launch(Dispatchers.Default) {
                val digest = MessageDigest.getInstance("SHA-256")
                val buffer = ByteArray(1024) { (it % 255).toByte() }
                var mathAcc = 1.0001
                while (isComputing.get() && isActive) {
                    // Heavy crypto and floating point math
                    digest.update(buffer)
                    digest.digest()
                    for (i in 0..150) {
                        mathAcc = Math.sin(mathAcc) * Math.cos(mathAcc) + 1.00001
                    }
                    operationCounter.addAndGet(250L)
                }
            }
        }

        // Ticker loop: evaluates progress every 1 second up to 120 seconds
        testJob = coroutineScope.launch(Dispatchers.Default) {
            var lastOpCount = 0L
            var peakGips = 0.0
            var gipsSum = 0.0
            var sampleCount = 0
            var minThrottle = 100
            val pointHistory = mutableListOf<ThrottlingPoint>()

            for (sec in 1..120) {
                delay(1000L)
                if (!isActive || !isComputing.get()) break

                val currentTotal = operationCounter.get()
                val deltaOps = currentTotal - lastOpCount
                lastOpCount = currentTotal

                // GIPS estimation: Billion operations per second scale
                val gips = (deltaOps.toDouble() / 15_000_000.0 * 100.0).roundToInt() / 100.0
                if (sec >= 3 && gips > peakGips) {
                    peakGips = gips
                } else if (peakGips == 0.0 && gips > 0) {
                    peakGips = gips
                }

                gipsSum += gips
                sampleCount++
                val avgGips = (gipsSum / sampleCount * 100.0).roundToInt() / 100.0

                val throttlePercent = if (peakGips > 0.0) {
                    ((gips / peakGips) * 100.0).roundToInt().coerceIn(15, 100)
                } else {
                    100
                }

                if (sec >= 5 && throttlePercent < minThrottle) {
                    minThrottle = throttlePercent
                }

                val currentTemp = readBatteryTemp()
                val currentPeakTemp = maxOf(_state.value.peakTempC, currentTemp)

                val pt = ThrottlingPoint(
                    second = sec,
                    scoreGips = gips,
                    throttlePercent = throttlePercent,
                    temperatureC = currentTemp
                )
                pointHistory.add(pt)

                _state.value = _state.value.copy(
                    elapsedSeconds = sec,
                    currentGips = gips,
                    peakGips = peakGips,
                    avgGips = avgGips,
                    currentThrottlePercent = throttlePercent,
                    minThrottlePercent = minThrottle,
                    currentTempC = currentTemp,
                    peakTempC = currentPeakTemp,
                    points = pointHistory.toList()
                )
            }

            // Finish test
            isComputing.set(false)
            val finalSustained = if (peakGips > 0.0) {
                ((gipsSum / sampleCount.coerceAtLeast(1) / peakGips) * 100.0).roundToInt().coerceIn(10, 100)
            } else 100

            val tempRise = ((_state.value.peakTempC - _state.value.startTempC) * 10.0).roundToInt() / 10.0

            val verdictRu = when {
                finalSustained >= 92 -> "ОТЛИЧНО: Система охлаждения справляется идеально! Устойчивость ${finalSustained}%, троттлинг отсутствует. Нагрев: +${tempRise}°C за 2 минуты стресса."
                finalSustained >= 80 -> "НОРМА: Умеренный троттлинг. Производительность снизилась до ${minThrottle}% (устойчивость ${finalSustained}%). Типично для тонких корпусов при долгой нагрузке (+${tempRise}°C)."
                else -> "ВНИМАНИЕ: СИЛЬНЫЙ ТРОТТЛИНГ! Процессор сбросил частоты до ${minThrottle}% из-за перегрева (+${tempRise}°C, пик ${_state.value.peakTempC}°C). Охлаждение не справляется."
            }

            val verdictUa = when {
                finalSustained >= 92 -> "ВІДМІННО: Система охолодження працює ідеально! Стійкість ${finalSustained}%, троттлінг відсутній. Нагрів: +${tempRise}°C за 2 хвилини стресу."
                finalSustained >= 80 -> "НОРМА: Помірний троттлінг. Продуктивність знизилась до ${minThrottle}% (стійкість ${finalSustained}%). Типово для тонких корпусів при тривалому навантаженні (+${tempRise}°C)."
                else -> "УВАГА: СИЛЬНИЙ ТРОТТЛІНГ! Процесор скинув частоти до ${minThrottle}% через перегрів (+${tempRise}°C, пік ${_state.value.peakTempC}°C). Охолодження не витримує."
            }

            val verdictEn = when {
                finalSustained >= 92 -> "EXCELLENT: Thermal cooling system handles load flawlessly! ${finalSustained}% sustained stability, zero severe throttling. Temp rise: +${tempRise}°C over 2 min."
                finalSustained >= 80 -> "MODERATE: Normal thermal throttling. Sustained performance ${finalSustained}%, throttled down to ${minThrottle}% (+${tempRise}°C). Standard for slim devices."
                else -> "WARNING: SEVERE THERMAL THROTTLING! CPU throttled down to ${minThrottle}% due to high thermal load (+${tempRise}°C, peak ${_state.value.peakTempC}°C)."
            }

            _state.value = _state.value.copy(
                isRunning = false,
                isFinished = true,
                verdictScore = finalSustained,
                verdictRu = verdictRu,
                verdictUa = verdictUa,
                verdictEn = verdictEn
            )
        }
    }

    fun stopTest() {
        isComputing.set(false)
        testJob?.cancel()
        _state.value = _state.value.copy(isRunning = false, isFinished = true)
    }

    fun resetTest() {
        stopTest()
        val temp = readBatteryTemp()
        _state.value = StressTestState(
            startTempC = temp,
            currentTempC = temp,
            peakTempC = temp
        )
    }

    private fun readBatteryTemp(): Float {
        return try {
            val ifilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            val batteryStatus = context.registerReceiver(null, ifilter)
            val tempRaw = batteryStatus?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 280) ?: 280
            tempRaw / 10.0f
        } catch (e: Throwable) {
            readCpuThermalZone() ?: 28.5f
        }
    }

    private fun readCpuThermalZone(): Float? {
        return try {
            val tzFile = File("/sys/class/thermal/thermal_zone0/temp")
            if (tzFile.exists() && tzFile.canRead()) {
                val t = tzFile.readText().trim().toLongOrNull() ?: return null
                if (t > 1000) t / 1000.0f else t.toFloat()
            } else null
        } catch (e: Throwable) { null }
    }
}
