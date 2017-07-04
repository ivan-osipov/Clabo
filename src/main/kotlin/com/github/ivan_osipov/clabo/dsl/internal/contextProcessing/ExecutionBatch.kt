package com.github.ivan_osipov.clabo.dsl.internal.contextProcessing

class ExecutionBatch {

    val callbacks: MutableList<() -> Unit> = ArrayList()

}