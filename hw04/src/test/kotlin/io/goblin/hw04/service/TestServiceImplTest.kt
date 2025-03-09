package io.goblin.hw04.service

import com.ninjasquad.springmockk.MockkBean
import io.goblin.hw04.dao.QuestionDao
import io.goblin.hw04.domain.Answer
import io.goblin.hw04.domain.Question
import io.goblin.hw04.domain.Student
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class TestServiceImplTest {
    @MockkBean
    private lateinit var ioService: LocalizedIOService

    @MockkBean
    private lateinit var questionDao: QuestionDao

    @Autowired
    private lateinit var testService: TestService

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

        every { ioService.printLine(any()) } just Runs
        every { ioService.printLineLocalized(any()) } just Runs
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
