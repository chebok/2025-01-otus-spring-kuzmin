package io.goblin.hw03.service

import io.goblin.hw03.dao.QuestionDao
import io.goblin.hw03.domain.Answer
import io.goblin.hw03.domain.Question
import io.goblin.hw03.domain.Student
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestServiceImplTest {
    private val ioService: LocalizedIOService = mockk(relaxed = true)
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
        every { ioService.readIntForRangeLocalized(any(), any(), any()) } returns 1

        val result = testService.executeTestFor(student)

        assertEquals(2, result.answeredQuestions.size)

        assertEquals(2, result.rightAnswersCount)

        verify { ioService.printLineLocalized("testService.answer.the.questions") }
        verify { ioService.printLine("What is Kotlin?") }
        verify { ioService.printLine("Why use Kotlin?") }
    }
}
