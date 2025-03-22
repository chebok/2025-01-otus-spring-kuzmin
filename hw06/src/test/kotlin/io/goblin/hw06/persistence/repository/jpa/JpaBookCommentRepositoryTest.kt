package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.model.BookComment
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(JpaBookCommentRepository::class)
class JpaBookCommentRepositoryTest {
    @Autowired
    private lateinit var em: TestEntityManager

    @Autowired
    private lateinit var bookCommentRepository: JpaBookCommentRepository

    @Test
    fun `should return correct book comments by book id`() {
        val bookId = 1L
        val expectedComments =
            commentIds
                .map { em.find(BookComment::class.java, it) }
                .filter { it.bookId == bookId }
        val actualComments = bookCommentRepository.findByBookId(bookId)
        actualComments.forEachIndexed { i, comment ->
            assertThat(comment).usingRecursiveComparison().isEqualTo(expectedComments[i])
        }
    }

    @Test
    fun `should return correct book comment by id`() {
        val bookCommentId = 1L
        val expectedComment = em.find(BookComment::class.java, bookCommentId)
        val actualComment = bookCommentRepository.findById(bookCommentId)
        assertThat(actualComment)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(expectedComment)
    }

    @Test
    fun `should save new book comment`() {
        val bookId = 1L
        val expectedComment =
            BookComment(
                id = null,
                text = "A captivating read that keeps you hooked from start to finish!",
                bookId = bookId,
            )
        val returnedComment = bookCommentRepository.save(expectedComment)
        assertThat(returnedComment)
            .isNotNull()
            .matches { it.id != null }
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expectedComment)

        assertThat(em.find(BookComment::class.java, returnedComment.id!!))
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(returnedComment)
    }

    @Test
    fun `should save updated book comment`() {
        val bookId = 1L
        val bookCommentId = 1L
        val commentWithChanges =
            BookComment(
                id = bookCommentId,
                text = "A captivating read that keeps you hooked from start to finish!",
                bookId = bookId,
            )
        val returnedCommentBeforeUpdate = em.find(BookComment::class.java, bookCommentId)

        assertThat(returnedCommentBeforeUpdate.text).isNotEqualTo(commentWithChanges.text)

        val returnedCommentAfterUpdate = bookCommentRepository.save(commentWithChanges)

        assertThat(returnedCommentAfterUpdate)
            .isNotNull()
            .matches { it.id != null }
            .usingRecursiveComparison()
            .ignoringExpectedNullFields()
            .isEqualTo(commentWithChanges)

        assertThat(em.find(BookComment::class.java, bookCommentId))
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(returnedCommentAfterUpdate)
    }

    @Test
    fun `should delete book comment`() {
        val bookCommentId = 1L
        val returnedCommentBeforeUpdate = em.find(BookComment::class.java, bookCommentId)
        assertThat(returnedCommentBeforeUpdate).isNotNull()
        em.detach(returnedCommentBeforeUpdate)
        bookCommentRepository.deleteById(bookCommentId)
        val returnedCommentAfterUpdate = em.find(BookComment::class.java, bookCommentId)
        assertThat(returnedCommentAfterUpdate).isNull()
    }

    companion object {
        private val commentIds = listOf(1, 2, 3, 4, 5, 6)
    }
}
