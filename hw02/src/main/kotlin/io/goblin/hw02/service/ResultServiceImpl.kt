package io.goblin.hw02.service

import io.goblin.hw02.config.TestConfig
import io.goblin.hw02.domain.TestResult
import org.springframework.stereotype.Service

@Service
class ResultServiceImpl(
    private val ioService: IOService,
    private val testConfig: TestConfig,
) : ResultService {
    override fun showResult(testResult: TestResult) {
        with(ioService) {
            printLine("")
            printLine("Test results:")
            printLine("Student: ${testResult.student.fullName}")
            printLine("Answered questions count: ${testResult.answeredQuestions.size}")
            printLine("Right answers count: ${testResult.rightAnswersCount}")

            printLine(
                if (testResult.rightAnswersCount >= testConfig.rightAnswersCountToPass) {
                    "Congratulations! You passed the test!"
                } else {
                    "Sorry. You failed the test."
                },
            )
        }
    }
}
