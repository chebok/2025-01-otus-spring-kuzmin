package io.goblin.hw07.service.impl

import io.goblin.hw07.mapper.toDto
import io.goblin.hw07.model.BookComment
import io.goblin.hw07.service.BookCommentService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(BookCommentServiceImpl::class)
class BookCommentServiceTest {
    @Autowired
    private lateinit var service: BookCommentService

    @Autowired
    private lateinit var em: TestEntityManager

    @Test
    fun `should return correct book comment by id`() {
        val commentId = 1L
        val expected = em.find(BookComment::class.java, commentId).toDto()

        val actual = service.findById(commentId)

        assertThat(actual)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(expected)
    }

    @Test
    fun `should return correct book comments by book id`() {
        val bookId = 1L
        val expected =
            commentIds
                .map { em.find(BookComment::class.java, it) }
                .filter { it.bookId == bookId }
                .map { it.toDto() }

        val actual = service.findByBookId(bookId)

        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected)
    }

    @Test
    fun `should save new book comment`() {
        val bookId = 1L
        val text = "This book blew my mind!"

        val result = service.insert(text, bookId)

        assertThat(result.id).isNotNull()

        val persisted = em.find(BookComment::class.java, result.id)
        assertThat(persisted.text).isEqualTo(text)
        assertThat(persisted.bookId).isEqualTo(bookId)
    }

    @Test
    fun `should save updated book comment`() {
        val existingId = 1L
        val updatedText = "A captivating read that keeps you hooked from start to finish!"
        val bookId = 1L

        val result = service.update(existingId, updatedText, bookId)

        val updated = em.find(BookComment::class.java, existingId)
        assertThat(updated.text).isEqualTo(updatedText)
        assertThat(result.text).isEqualTo(updatedText)
    }

    @Test
    fun `should delete comment`() {
        val commentId = 1L
        assertThat(em.find(BookComment::class.java, commentId)).isNotNull()

        service.deleteById(commentId)

        assertThat(em.find(BookComment::class.java, commentId)).isNull()
    }

    companion object {
        private val commentIds = listOf(1L, 2L, 3L, 4L, 5L, 6L)
    }
}
