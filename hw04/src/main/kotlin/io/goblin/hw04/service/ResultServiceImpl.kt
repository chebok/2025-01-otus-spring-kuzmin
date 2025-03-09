package io.goblin.hw04.service

import io.goblin.hw04.config.properties.TestConfig
import io.goblin.hw04.domain.TestResult
import org.springframework.stereotype.Service

@Service
class ResultServiceImpl(
    private val ioService: LocalizedIOService,
    private val testConfig: TestConfig,
) : ResultService {
    override fun showResult(testResult: TestResult) {
        ioService.printLine("")
        ioService.printLineLocalized(TEST_RESULTS_CODE)
        ioService.printFormattedLineLocalized(STUDENT_CODE, testResult.student.fullName)
        ioService.printFormattedLineLocalized(ANSWERED_QUESTION_COUNT_CODE, testResult.answeredQuestions.size)
        ioService.printFormattedLineLocalized(RIGHT_ANSWERS_COUNT_CODE, testResult.rightAnswersCount)

        ioService.printLineLocalized(
            if (testResult.rightAnswersCount >= testConfig.rightAnswersCountToPass) {
                PASSED_TEST_CODE
            } else {
                FAIL_TEST_CODE
            },
        )
    }

    companion object {
        private const val TEST_RESULTS_CODE = "resultService.test.results"
        private const val STUDENT_CODE = "resultService.student"
        private const val ANSWERED_QUESTION_COUNT_CODE = "resultService.answered.questions.count"
        private const val RIGHT_ANSWERS_COUNT_CODE = "resultService.right.answers.count"
        private const val PASSED_TEST_CODE = "resultService.passed.test"
        private const val FAIL_TEST_CODE = "resultService.fail.test"
    }
}
