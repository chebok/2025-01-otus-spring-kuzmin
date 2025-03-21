package io.goblin.hw03.dao

import io.goblin.hw03.config.properties.TestFileNameProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class CsvQuestionDaoTest {
    private lateinit var csvQuestionDao: CsvQuestionDao

    @BeforeEach
    fun setUp() {
        val testFileNameProvider =
            object : TestFileNameProvider {
                override val testFileName: String = "questions_test.csv"
            }
        csvQuestionDao = CsvQuestionDao(testFileNameProvider)
    }

    @Test
    @Tag("integration")
    fun `should read one question from CSV and parse answers correctly`() {
        val questions = csvQuestionDao.findAll()

        assertThat(questions).hasSize(1)
        assertThat(questions[0].text).isEqualTo("What is Kotlin?")
        assertThat(questions[0].answers).hasSize(3)
        assertThat(questions[0].answers[0].text).isEqualTo("A programming language")
        assertThat(questions[0].answers[0].isCorrect).isTrue()
    }
}
