package io.goblin.hw04.dao

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CsvQuestionDaoTest(
    private val csvQuestionDao: CsvQuestionDao,
) {
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
