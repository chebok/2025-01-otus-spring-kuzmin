package io.goblin.hw02.service

import io.goblin.hw02.dao.QuestionDao
import io.goblin.hw02.domain.Answer
import io.goblin.hw02.domain.Question
import io.goblin.hw02.domain.Student
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestServiceImplTest {
    private val ioService: IOService = mockk(relaxed = true)
    private val questionDao: QuestionDao = mockk()
    private val testService = TestServiceImpl(ioService, questionDao)

    @Test
    fun `should execute test and return correct TestResult`() {
        val student = Student("John", "Doe")

        val question1 =
            Question(
                text = "What is Kotlin?",
                answers =
                    listOf(
                        Answer("A JVM language", isCorrect = true),
                        Answer("A scripting language", isCorrect = false),
                    ),
            )

        val question2 =
            Question(
                text = "Why use Kotlin?",
                answers =
                    listOf(
                        Answer("Less boilerplate", isCorrect = true),
                        Answer("Harder than Java", isCorrect = false),
                    ),
            )

        val questions = listOf(question1, question2)

        every { questionDao.findAll() } returns questions
        every { ioService.readIntForRange(any(), any(), any()) } returns 1

        val result = testService.executeTestFor(student)

        assertEquals(2, result.answeredQuestions.size)

        assertEquals(2, result.rightAnswersCount)

        verify { ioService.printFormattedLine("Please answer the questions below") }
        verify { ioService.printFormattedLine("What is Kotlin?") }
        verify { ioService.printFormattedLine("Why use Kotlin?") }
    }
}
