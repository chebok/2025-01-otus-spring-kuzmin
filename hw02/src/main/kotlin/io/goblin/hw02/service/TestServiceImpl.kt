package io.goblin.hw02.service

import io.goblin.hw02.dao.QuestionDao
import io.goblin.hw02.domain.Student
import io.goblin.hw02.domain.TestResult
import org.springframework.stereotype.Service

@Service
class TestServiceImpl(
    private val ioService: IOService,
    private val questionDao: QuestionDao,
) : TestService {
    override fun executeTestFor(student: Student): TestResult {
        ioService.printLine("")

        val questions = questionDao.findAll()
        val testResult = TestResult(student)

        ioService.printFormattedLine("Please answer the questions below")

        questions.forEach { question ->
            ioService.printFormattedLine(question.text)
            question.answers.forEachIndexed { ansIndex, answer ->
                ioService.printFormattedLine("${ansIndex + 1}) ${answer.text}")
            }
            ioService.readIntForRange(1, question.answers.size, "Please input answer number").let {
                val isRightAnswer = question.answers[it - 1].isCorrect
                testResult.applyAnswer(question, isRightAnswer)
            }
        }
        return testResult
    }
}
