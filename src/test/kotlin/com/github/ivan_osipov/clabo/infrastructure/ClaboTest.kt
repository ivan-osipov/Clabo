package com.github.ivan_osipov.clabo.infrastructure

import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(ClaboJupiterExtension::class)
annotation class ClaboTest