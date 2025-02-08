package io.goblin.hw01

import io.goblin.hw01.service.TestRunnerService
import org.springframework.beans.factory.getBean
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main() {
    ClassPathXmlApplicationContext("applicationContext.xml").use { context ->
        val testRunnerService = context.getBean<TestRunnerService>()
        testRunnerService.run()
    }
}
