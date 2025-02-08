package io.goblin.hw01.service

import io.goblin.hw01.dao.QuestionDao
import io.goblin.hw01.domain.Answer
import io.goblin.hw01.domain.Question
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class TestServiceImplTest {
    private val ioService = mockk<IOService>(relaxed = true)
    private val questionDao = mockk<QuestionDao>(relaxed = true)
    private val testService = TestServiceImpl(ioService, questionDao)

    @Test
    fun `should print all questions`() {
        val questions =
            listOf(
                Question("What is Kotlin?", listOf(Answer("A JVM language", true))),
                Question("Why use Kotlin?", listOf(Answer("Less boilerplate", true))),
            )

        every { questionDao.findAll() } returns questions

        testService.executeTest()

        verify { ioService.printFormattedLine("Please answer the questions below") }
        verify { ioService.printFormattedLine("1. What is Kotlin?") }
    }
}
