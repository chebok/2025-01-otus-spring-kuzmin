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

        questions.forEachIndexed { index, question ->
            ioService.printFormattedLine("${index + 1}. ${question.text}")
            question.answers.forEach { answer ->
                ioService.printFormattedLine("- ${answer.text}")
            }
        }
    }
}
