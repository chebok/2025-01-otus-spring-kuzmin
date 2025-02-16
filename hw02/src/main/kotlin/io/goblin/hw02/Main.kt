package io.goblin.hw02

import io.goblin.hw02.service.TestRunnerService
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    AnnotationConfigApplicationContext(AppConfig::class.java).use { context ->
        val testRunnerService = context.getBean<TestRunnerService>()
        testRunnerService.run()
    }
}
