package io.goblin.hw01.service

import io.goblin.hw01.dao.QuestionDao

class TestServiceImpl(
    private val ioService: IOService,
    private val questionDao: QuestionDao,
) : TestService {
    override fun executeTest() {
        ioService.printLine("")

        val questions = questionDao.findAll()
        if (questions.isEmpty()) {
            ioService.printLine("No questions found!")
            return
        }

        ioService.printFormattedLine("Please answer the questions below")

        questions.forEach { question ->
            ioService.printFormattedLine(question.text)
            question.answers.forEachIndexed { ansIndex, answer ->
                ioService.printFormattedLine("${ansIndex + 1}) ${answer.text}")
            }
        }
    }
}
