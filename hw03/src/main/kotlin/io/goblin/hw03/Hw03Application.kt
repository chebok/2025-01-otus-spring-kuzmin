package io.goblin.hw03

import io.goblin.hw03.service.TestRunnerService
import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Hw03Application

fun main(args: Array<String>) {
    runApplication<Hw03Application>(*args).use { context ->
        context.getBean<TestRunnerService>().run()
    }
}
