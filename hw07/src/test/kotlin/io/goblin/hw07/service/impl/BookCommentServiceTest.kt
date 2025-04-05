package io.goblin.hw07.service.impl

import io.goblin.hw07.mapper.toDto
import io.goblin.hw07.persistence.repository.BookCommentRepository
import io.goblin.hw07.service.BookCommentService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@DataJpaTest
@ActiveProfiles("test")
@Import(BookCommentServiceImpl::class)
@Sql(scripts = ["/data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BookCommentServiceTest {
    @Autowired
    private lateinit var service: BookCommentService

    @Autowired
    private lateinit var repository: BookCommentRepository

    @Test
    @Transactional(propagation = Propagation.NEVER)
    fun `should return correct book comment by id`() {
        val commentId = 1L
        val expected = repository.findById(commentId).getOrNull()?.toDto()

        val actual = service.findById(commentId)

        assertThat(actual)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(expected)
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    fun `should return correct book comments by book id`() {
        val bookId = 1L
        val expected =
            commentIds
                .mapNotNull { repository.findByIdOrNull(it) }
                .filter { it.bookId == bookId }
                .map { it.toDto() }

        val actual = service.findByBookId(bookId)

        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected)
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    fun `should save new book comment`() {
        val bookId = 1L
        val text = "This book blew my mind!"

        val result = service.insert(text, bookId)

        assertThat(result.id).isNotNull()

        val persisted = repository.findById(result.id).getOrNull()?.toDto()
        assertThat(persisted?.text).isEqualTo(text)
        assertThat(persisted?.bookId).isEqualTo(bookId)
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    fun `should save updated book comment`() {
        val existingId = 1L
        val updatedText = "A captivating read that keeps you hooked from start to finish!"

        val result = service.update(existingId, updatedText)

        val updated = repository.findById(existingId).getOrNull()?.toDto()
        assertThat(updated?.text).isEqualTo(updatedText)
        assertThat(result.text).isEqualTo(updatedText)
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    fun `should delete comment`() {
        val commentId = 1L
        assertThat(repository.findById(commentId).getOrNull()).isNotNull()

        service.deleteById(commentId)

        assertThat(repository.findById(commentId).getOrNull()).isNull()
    }

    companion object {
        private val commentIds = listOf(1L, 2L, 3L, 4L, 5L, 6L)
    }
}
