package io.goblin.hw03.service

import io.goblin.hw03.dao.QuestionDao
import io.goblin.hw03.domain.Student
import io.goblin.hw03.domain.TestResult
import org.springframework.stereotype.Service

@Service
class TestServiceImpl(
    private val ioService: LocalizedIOService,
    private val questionDao: QuestionDao,
) : TestService {
    override fun executeTestFor(student: Student): TestResult {
        ioService.printLine("")

        val questions = questionDao.findAll()
        val testResult = TestResult(student)

        ioService.printLineLocalized(ANSWER_QUESTIONS_CODE)

        questions.forEach { question ->
            ioService.printLine(question.text)
            question.answers.forEachIndexed { ansIndex, answer ->
                ioService.printLine("${ansIndex + 1}) ${answer.text}")
            }
            ioService.readIntForRangeLocalized(1, question.answers.size, WRONG_ANSWER_FORMAT_CODE).let {
                val isRightAnswer = question.answers[it - 1].isCorrect
                testResult.applyAnswer(question, isRightAnswer)
            }
        }
        return testResult
    }

    companion object {
        private const val ANSWER_QUESTIONS_CODE = "testService.answer.the.questions"
        private const val WRONG_ANSWER_FORMAT_CODE = "testService.input.wrong.answer.format"
    }
}
