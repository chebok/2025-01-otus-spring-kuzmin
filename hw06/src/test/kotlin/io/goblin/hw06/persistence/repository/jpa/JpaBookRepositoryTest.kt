package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.model.Author
import io.goblin.hw06.model.Book
import io.goblin.hw06.model.Genre
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(JpaBookRepository::class)
class JpaBookRepositoryTest {
    @Autowired
    private lateinit var em: TestEntityManager

    @Autowired
    private lateinit var bookRepository: JpaBookRepository

    @Test
    fun `should return correct book list`() {
        val expectedBooks = bookIds.map { em.find(Book::class.java, it) }
        val actualBooks = bookRepository.findAll()
        actualBooks.forEachIndexed { i, book ->
            assertThat(book).usingRecursiveComparison().isEqualTo(expectedBooks[i])
        }
    }

    @ParameterizedTest(name = "should return correct book by id {0}")
    @ValueSource(longs = [1L, 2L, 3L])
    fun `should return correct book by id`(expectedBookId: Long) {
        val expectedBook = em.find(Book::class.java, expectedBookId)
        val actualBook = bookRepository.findById(expectedBookId)
        assertThat(actualBook)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(expectedBook)
    }

    @Test
    fun `should save new book`() {
        val author = em.find(Author::class.java, 1L)
        val genres = longArrayOf(2L, 5L).map { em.find(Genre::class.java, it) }.toList()
        val expectedBook =
            Book(
                id = null,
                title = "Whispers of the Forgotten Realm",
                author = author,
                genres = genres,
            )
        val returnedBook = bookRepository.save(expectedBook)
        assertThat(returnedBook)
            .isNotNull()
            .matches { it.id != null }
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expectedBook)

        assertThat(em.find(Book::class.java, returnedBook.id!!))
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(returnedBook)
    }

    @Test
    fun `should save updated book`() {
        val bookId = 1L
        val author = em.find(Author::class.java, 1L)
        val genres = longArrayOf(2L, 5L).map { em.find(Genre::class.java, it) }.toList()
        val bookWithChanges =
            Book(
                id = bookId,
                title = "Whispers of the Forgotten Realm",
                author = author,
                genres = genres,
            )
        val returnedBookBeforeUpdate = em.find(Book::class.java, bookId)

        assertThat(returnedBookBeforeUpdate.title).isNotEqualTo(bookWithChanges.title)

        val returnedBookAfterUpdate = bookRepository.save(bookWithChanges)

        assertThat(returnedBookAfterUpdate)
            .isNotNull()
            .matches { it.id != null }
            .usingRecursiveComparison()
            .ignoringExpectedNullFields()
            .isEqualTo(bookWithChanges)

        assertThat(em.find(Book::class.java, bookId))
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(returnedBookAfterUpdate)
    }

    @Test
    fun `should delete book`() {
        val bookId = 1L
        val returnedBookBeforeDelete = em.find(Book::class.java, bookId)
        assertThat(returnedBookBeforeDelete).isNotNull()
        em.detach(returnedBookBeforeDelete)
        bookRepository.deleteById(bookId)
        val returnedBookAfterDelete = em.find(Book::class.java, bookId)
        assertThat(returnedBookAfterDelete).isNull()
    }

    companion object {
        private val bookIds = listOf(1, 2, 3)
    }
}
