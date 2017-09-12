package com.github.ivan_osipov.clabo.infrastructure

import com.github.ivan_osipov.clabo.infrastructure.dsl.TEST_SERVER_PORT
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import spark.Service
import java.lang.reflect.AnnotatedElement
import kotlin.reflect.full.createInstance

class ClaboJupiterExtension : BeforeEachCallback, AfterEachCallback {
    private var service: Service? = null
    override fun beforeEach(context: ExtensionContext) {
        if(!context.element.isPresent) return
        val element: AnnotatedElement = context.element.get()
        val testClass = context.testClass
        val useServerAnnotation = UseServer::class.java
        val annotation = element.getAnnotation(useServerAnnotation) ?: testClass.map { it.getAnnotation(useServerAnnotation) }.orElse(null)
        if(annotation != null) {
            val serverInit = annotation.telegramServerExtension.createInstance()
            val service = Service.ignite()
            service.port(TEST_SERVER_PORT)
            with(serverInit) {
                service.setup()
            }
            this.service = service
        }

    }

    override fun afterEach(context: ExtensionContext) {
        this.service?.stop()
    }
}