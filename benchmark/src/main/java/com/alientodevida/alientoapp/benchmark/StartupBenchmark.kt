package com.alientodevida.alientoapp.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupCompilationNone() = startup(CompilationMode.None())

    @Test
    fun startupCompilationPartial() = startup(CompilationMode.Partial())

    @Test
    fun startupCompilationFull() = startup(CompilationMode.Full())

    private fun startup(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.alientodevida.app",
        metrics = listOf(StartupTimingMetric()),
        iterations = 3,
        compilationMode = compilationMode,
        startupMode = StartupMode.COLD,
    ) {
        pressHome()
        startActivityAndWait()
    }
}
