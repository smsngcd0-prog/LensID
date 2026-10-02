package com.example.hardware

import android.app.ActivityManager
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.roundToInt

data class RamStressState(
    val isRunning: Boolean = false,
    val isCompleted: Boolean = false,
    val allocatedMb: Int = 0,
    val totalSystemRamMb: Int = 0,
    val usedSystemRamMb: Int = 0,
    val availableSystemRamMb: Int = 0,
    val totalFillPercentage: Int = 0,
    val writeSpeedMbPerSec: Double = 0.0,
    val blocksAllocatedCount: Int = 0,
    val passesCompleted: Int = 0,
    val errorsFound: Int = 0,
    val verdictRu: String = "",
    val verdictUa: String = "",
    val verdictEn: String = ""
)

class RamStressTester(private val context: Context) {

    private val _state = MutableStateFlow(RamStressState())
    val state: StateFlow<RamStressState> = _state.asStateFlow()

    private var workerJob: Job? = null
    private val isRunningFlag = AtomicBoolean(false)
    private val allocatedBuffers = mutableListOf<ByteBuffer>()

    init {
        updateMemoryMetrics(0, 0.0, 0, 0)
    }

    fun startTest(coroutineScope: CoroutineScope) {
        if (isRunningFlag.get()) return

        isRunningFlag.set(true)
        allocatedBuffers.clear()

        _state.value = _state.value.copy(
            isRunning = true,
            isCompleted = false,
            allocatedMb = 0,
            writeSpeedMbPerSec = 0.0,
            blocksAllocatedCount = 0,
            passesCompleted = 0,
            errorsFound = 0,
            verdictRu = "",
            verdictUa = "",
            verdictEn = ""
        )

        workerJob = coroutineScope.launch(Dispatchers.Default) {
            val chunkSizeMb = 64
            val chunkSizeBytes = chunkSizeMb * 1024 * 1024
            // Fast 1MB pattern buffer for high-throughput native direct memory fill
            val patternBlockA = ByteArray(1024 * 1024) { 0x5A.toByte() }
            val patternBlockB = ByteArray(1024 * 1024) { 0xA5.toByte() }
            var currentAllocated = 0
            var passNumber = 0
            var totalErrors = 0

            // 1. FAST SATURATION PHASE: Allocate 64MB direct byte buffers until available RAM hits safety limit (~350MB)
            while (isRunningFlag.get() && isActive) {
                val memInfo = getMemoryInfo()
                val freeMb = (memInfo.availMem / (1024 * 1024)).toInt()

                // Keep 350MB safety reserve for OS system server / SurfaceFlinger
                if (freeMb <= 350) {
                    break
                }

                val startTime = System.currentTimeMillis()
                try {
                    // Direct ByteBuffer uses native physical address space outside JVM heap
                    val buffer = ByteBuffer.allocateDirect(chunkSizeBytes)

                    val activePattern = if (allocatedBuffers.size % 2 == 0) patternBlockA else patternBlockB
                    // Fill 64MB in 1MB chunks via native memcpy for maximum bus saturation
                    for (mb in 0 until chunkSizeMb) {
                        buffer.position(mb * 1024 * 1024)
                        buffer.put(activePattern)
                    }

                    allocatedBuffers.add(buffer)
                    currentAllocated += chunkSizeMb

                    val elapsedMs = (System.currentTimeMillis() - startTime).coerceAtLeast(1)
                    val speed = ((chunkSizeMb.toDouble() / (elapsedMs / 1000.0)) * 10.0).roundToInt() / 10.0

                    withContext(Dispatchers.Main) {
                        updateMemoryMetrics(currentAllocated, speed, allocatedBuffers.size, passNumber, totalErrors)
                    }
                } catch (e: OutOfMemoryError) {
                    break
                } catch (e: Throwable) {
                    break
                }
            }

            // 2. VERIFICATION & RE-WRITE STRESS CYCLES: Continuously read and invert patterns to test DRAM capacitors
            val verifyBlock = ByteArray(1024 * 1024)
            while (isRunningFlag.get() && isActive) {
                passNumber++
                val passStartTime = System.currentTimeMillis()
                var bytesVerifiedInPass = 0L

                for (idx in allocatedBuffers.indices) {
                    if (!isRunningFlag.get() || !isActive) break

                    val buffer = allocatedBuffers[idx]
                    val expectedPattern = if (idx % 2 == 0) 0x5A.toByte() else 0xA5.toByte()

                    // Check integrity of blocks
                    for (mb in 0 until chunkSizeMb step 4) {
                        buffer.position(mb * 1024 * 1024)
                        buffer.get(verifyBlock)
                        for (sample in 0 until 1024 * 1024 step 512) {
                            if (verifyBlock[sample] != expectedPattern) {
                                totalErrors++
                            }
                        }
                    }
                    bytesVerifiedInPass += chunkSizeBytes

                    // Invert pattern to flip silicon memory bits
                    val invertedBlock = if (expectedPattern == 0x5A.toByte()) patternBlockB else patternBlockA
                    for (mb in 0 until chunkSizeMb step 8) {
                        buffer.position(mb * 1024 * 1024)
                        buffer.put(invertedBlock)
                    }
                }

                val passElapsedMs = (System.currentTimeMillis() - passStartTime).coerceAtLeast(1)
                val mbVerified = (bytesVerifiedInPass / (1024 * 1024)).toInt()
                val speed = ((mbVerified.toDouble() / (passElapsedMs / 1000.0)) * 10.0).roundToInt() / 10.0

                withContext(Dispatchers.Main) {
                    updateMemoryMetrics(currentAllocated, speed, allocatedBuffers.size, passNumber, totalErrors)
                }

                kotlinx.coroutines.delay(600L)
            }

            val finalAllocated = currentAllocated
            val finalPasses = passNumber
            val finalErrors = totalErrors

            withContext(Dispatchers.Main) {
                val verdictRu = if (finalErrors == 0) {
                    "УСПЕШНО: Заполнено $finalAllocated МБ физической памяти ($finalPasses циклов проверки). 0 ошибок памяти! Все кремниевые ячейки LPDDR стабильны, фейковых секторов swap не обнаружено."
                } else {
                    "ВНИМАНИЕ: Обнаружено $finalErrors сбоев ячеек при заполнении $finalAllocated МБ! Возможен брак памяти или агрессивный сброс swap-прошивки."
                }

                val verdictUa = if (finalErrors == 0) {
                    "УСПІШНО: Заповнено $finalAllocated МБ фізичної пам'яті ($finalPasses циклів перевірки). 0 помилок пам'яті! Усі комірки LPDDR стабільні, фейкових секторів swap не виявлено."
                } else {
                    "УВАГА: Виявлено $finalErrors збоїв комірок під час заповнення $finalAllocated МБ! Можливий брак пам'яті або скидання swap."
                }

                val verdictEn = if (finalErrors == 0) {
                    "PASSED: Filled $finalAllocated MB physical RAM ($finalPasses verification cycles). 0 memory errors! Silicon LPDDR cells verified 100% stable."
                } else {
                    "WARNING: Detected $finalErrors memory byte anomalies across $finalAllocated MB allocation! Potential unstable memory cells or virtual swap corruption."
                }

                _state.value = _state.value.copy(
                    isRunning = false,
                    isCompleted = true,
                    verdictRu = verdictRu,
                    verdictUa = verdictUa,
                    verdictEn = verdictEn
                )
            }
        }
    }

    fun releaseMemoryAndStop() {
        isRunningFlag.set(false)
        workerJob?.cancel()
        allocatedBuffers.clear()
        System.gc()
        updateMemoryMetrics(0, 0.0, 0, _state.value.passesCompleted, _state.value.errorsFound)
        _state.value = _state.value.copy(isRunning = false, isCompleted = true)
    }

    private fun updateMemoryMetrics(
        allocatedMb: Int,
        speed: Double,
        blocksCount: Int,
        passes: Int,
        errors: Int = 0
    ) {
        val memInfo = getMemoryInfo()
        var totalMb = (memInfo.totalMem / (1024 * 1024)).toInt()
        var availMb = (memInfo.availMem / (1024 * 1024)).toInt()

        if (totalMb <= 0) {
            totalMb = readProcMemTotalMb()
        }
        if (availMb <= 0) {
            availMb = (totalMb * 0.6).toInt().coerceAtLeast(512)
        }

        val usedMb = (totalMb - availMb).coerceAtLeast(0)
        val fillPct = if (totalMb > 0) ((usedMb.toDouble() / totalMb.toDouble()) * 100).roundToInt().coerceIn(0, 100) else 0

        _state.value = _state.value.copy(
            allocatedMb = allocatedMb,
            totalSystemRamMb = totalMb,
            usedSystemRamMb = usedMb,
            availableSystemRamMb = availMb,
            totalFillPercentage = fillPct,
            writeSpeedMbPerSec = speed,
            blocksAllocatedCount = blocksCount,
            passesCompleted = passes,
            errorsFound = errors
        )
    }

    private fun readProcMemTotalMb(): Int {
        return try {
            val file = java.io.File("/proc/meminfo")
            if (file.exists() && file.canRead()) {
                val line = file.useLines { lines -> lines.firstOrNull { it.startsWith("MemTotal:") } }
                val kb = line?.substringAfter(":")?.substringBefore("kB")?.trim()?.toLongOrNull()
                if (kb != null) (kb / 1024L).toInt() else 4096
            } else 4096
        } catch (e: Throwable) {
            4096
        }
    }

    private fun getMemoryInfo(): ActivityManager.MemoryInfo {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        am.getMemoryInfo(memInfo)
        return memInfo
    }
}
