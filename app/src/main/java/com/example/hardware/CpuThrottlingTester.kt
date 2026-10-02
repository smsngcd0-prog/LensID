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

    private var tickerJob: Job? = null
    private val isComputing = AtomicBoolean(false)
    private val operationCounter = AtomicLong(0L)
    private val workerThreads = mutableListOf<Thread>()

    fun startTest(coroutineScope: CoroutineScope) {
        if (isComputing.get()) return

        stopInternal()
        val initialTemp = readBatteryTemp()
        _state.value = StressTestState(
            isRunning = true,
            isFinished = false,
            elapsedSeconds = 0,
            startTempC = initialTemp,
            currentTempC = initialTemp,
            peakTempC = initialTemp,
            points = emptyList(),
            currentThrottlePercent = 100,
            minThrottlePercent = 100
        )

        isComputing.set(true)
        operationCounter.set(0L)
        workerThreads.clear()

        // Saturate all CPU cores: Spawn 2x threads per physical core to keep CPU pipelines 100% full
        val coreCount = Runtime.getRuntime().availableProcessors().coerceAtLeast(2)
        val threadCount = (coreCount * 2).coerceIn(4, 16)

        for (threadIdx in 0 until threadCount) {
            val worker = Thread({
                try {
                    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_DISPLAY)
                } catch (e: Throwable) {}

                // Pre-allocated arrays per thread - ZERO HEAP ALLOCATIONS in the tight compute loop
                val matrixA = FloatArray(64) { (it + threadIdx) * 1.05f }
                val matrixB = FloatArray(64) { (63 - it + threadIdx) * 0.95f }
                val matrixC = FloatArray(64)

                var xorState = 0x5a5cd789635d2d11L + (threadIdx * 7919L)
                var localCounter = 0L

                val cr = -0.743643887037158704752191506114774
                val ci = 0.131825904205311970493132056385139

                while (isComputing.get()) {
                    // 1. Heavy 8x8 matrix multiplication (SIMD / Vector & FPU stress)
                    for (i in 0 until 8) {
                        val rowOff = i * 8
                        for (j in 0 until 8) {
                            var sum = 0f
                            for (k in 0 until 8) {
                                sum += matrixA[rowOff + k] * matrixB[k * 8 + j]
                            }
                            matrixC[rowOff + j] = sum
                        }
                    }

                    // 2. High-throughput 64-bit integer ALU mix (XorShift64)
                    for (i in 0..100) {
                        xorState = xorState xor (xorState shl 13)
                        xorState = xorState xor (xorState ushr 7)
                        xorState = xorState xor (xorState shl 17)
                    }

                    // 3. Double-precision Mandelbrot escape polynomial (ALU & Floating-Point units)
                    var zr = 0.0
                    var zi = 0.0
                    for (i in 0..50) {
                        val zr2 = zr * zr
                        val zi2 = zi * zi
                        if (zr2 + zi2 > 4.0) {
                            zr = 0.0
                            zi = 0.0
                        }
                        zi = 2.0 * zr * zi + ci
                        zr = zr2 - zi2 + cr
                    }

                    // 4. Prime check sweep (Branch prediction & integer division)
                    var primesFound = 0
                    for (n in 600..650) {
                        var isP = true
                        for (d in 2..23) {
                            if (n % d == 0) {
                                isP = false
                                break
                            }
                        }
                        if (isP) primesFound++
                    }

                    // Batch update: only flush to atomic counter every 60,000 ops to eliminate cache contention!
                    localCounter += 2000L
                    if (localCounter >= 60_000L) {
                        operationCounter.addAndGet(localCounter)
                        localCounter = 0L
                    }
                }
            }, "CpuHeavyStress-$threadIdx")

            worker.isDaemon = true
            workerThreads.add(worker)
            worker.start()
        }

        // Ticker loop on Main thread: runs indefinitely without timer until user stops
        tickerJob = coroutineScope.launch(Dispatchers.Main.immediate) {
            var lastOpCount = 0L
            var peakGips = 0.0
            var gipsSum = 0.0
            var sampleCount = 0
            var minThrottle = 100
            val pointHistory = mutableListOf<ThrottlingPoint>()
            var sec = 0

            while (isComputing.get() && isActive) {
                delay(1000L)
                if (!isComputing.get()) break
                sec++

                val currentTotal = operationCounter.get()
                val deltaOps = (currentTotal - lastOpCount).coerceAtLeast(0L)
                lastOpCount = currentTotal

                // GIPS calculation (Billion Operations Per Second)
                val gips = ((deltaOps.toDouble() / 10_000_000.0) * 10.0).roundToInt() / 10.0
                if (sec >= 2 && gips > peakGips) {
                    peakGips = gips
                } else if (peakGips == 0.0 && gips > 0) {
                    peakGips = gips
                }

                gipsSum += gips
                sampleCount++
                val avgGips = ((gipsSum / sampleCount) * 10.0).roundToInt() / 10.0

                val throttlePercent = if (peakGips > 0.0) {
                    ((gips / peakGips) * 100.0).roundToInt().coerceIn(10, 100)
                } else {
                    100
                }

                if (sec >= 4 && throttlePercent < minThrottle) {
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
                // Keep smooth history up to 600 points (10 minutes)
                if (pointHistory.size > 600) {
                    pointHistory.removeAt(0)
                }

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
        }
    }

    fun stopTest() {
        if (!isComputing.get()) return
        stopInternal()

        val st = _state.value
        val peak = st.peakGips
        val avg = st.avgGips
        val finalSustained = if (peak > 0.0) {
            ((avg / peak) * 100.0).roundToInt().coerceIn(10, 100)
        } else 100

        val tempRise = ((st.peakTempC - st.startTempC) * 10.0).roundToInt() / 10.0
        val durationMin = st.elapsedSeconds / 60
        val durationSec = st.elapsedSeconds % 60
        val timeLabel = "${durationMin}м ${durationSec}с"

        val verdictRu = when {
            finalSustained >= 90 -> "ОТЛИЧНО: Система охлаждения справляется превосходно! Устойчивость ${finalSustained}%, троттлинг отсутствует за $timeLabel нагрузки на 100% всех ядер (Нагрев: +${tempRise}°C, пик ${st.peakTempC}°C)."
            finalSustained >= 78 -> "НОРМА: Умеренный троттлинг. Производительность снизилась до ${st.minThrottlePercent}% (устойчивость ${finalSustained}% за $timeLabel нагрузки). Нагрев: +${tempRise}°C."
            else -> "ВНИМАНИЕ: СИЛЬНЫЙ ТРОТТЛИНГ! Процессор сбросил частоты до ${st.minThrottlePercent}% (устойчивость ${finalSustained}% за $timeLabel нагрузки). Пик температуры: ${st.peakTempC}°C (+${tempRise}°C)."
        }

        val verdictUa = when {
            finalSustained >= 90 -> "ВІДМІННО: Охолодження відмінне! Стійкість ${finalSustained}%, троттлінг відсутній за $timeLabel навантаження на всі ядра (Нагрів: +${tempRise}°C, пік ${st.peakTempC}°C)."
            finalSustained >= 78 -> "НОРМА: Помірний троттлінг. Продуктивність знизилась до ${st.minThrottlePercent}% (стійкість ${finalSustained}% за $timeLabel). Нагрів: +${tempRise}°C."
            else -> "УВАГА: СИЛЬНИЙ ТРОТТЛІНГ! Процесор скинув частоти до ${st.minThrottlePercent}% за $timeLabel. Пік температури: ${st.peakTempC}°C (+${tempRise}°C)."
        }

        val verdictEn = when {
            finalSustained >= 90 -> "EXCELLENT: Thermal dissipation is outstanding! ${finalSustained}% sustained stability over $timeLabel 100% all-core load (Temp rise: +${tempRise}°C, peak ${st.peakTempC}°C)."
            finalSustained >= 78 -> "MODERATE: Normal thermal throttling. Sustained ${finalSustained}%, throttled to ${st.minThrottlePercent}% over $timeLabel (+${tempRise}°C)."
            else -> "WARNING: SEVERE THERMAL THROTTLING! CPU throttled to ${st.minThrottlePercent}% over $timeLabel. Peak temp: ${st.peakTempC}°C (+${tempRise}°C)."
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

    private fun stopInternal() {
        isComputing.set(false)
        tickerJob?.cancel()

        for (thread in workerThreads) {
            try {
                thread.interrupt()
            } catch (e: Throwable) {}
        }
        workerThreads.clear()
    }

    fun resetTest() {
        stopInternal()
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
            28.5f
        }
    }
}
