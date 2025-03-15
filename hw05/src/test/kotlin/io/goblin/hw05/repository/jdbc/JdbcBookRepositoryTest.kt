package io.goblin.hw05.repository.jdbc

import io.goblin.hw05.model.Author
import io.goblin.hw05.model.Book
import io.goblin.hw05.model.Genre
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import

@JdbcTest
@Import(JdbcBookRepository::class)
class JdbcBookRepositoryTest {
    @Autowired
    private lateinit var bookRepository: JdbcBookRepository

    private lateinit var authors: List<Author>
    private lateinit var genres: List<Genre>
    private lateinit var books: List<Book>

    @BeforeEach
    fun setUp() {
        authors = getDbAuthors()
        genres = getDbGenres()
        books = getDbBooks(authors, genres)
    }

    @Test
    fun `should return correct book list`() {
        val actualBooks = bookRepository.findAll()
        assertThat(actualBooks).containsExactlyElementsOf(books)
    }

    @ParameterizedTest(name = "should return correct book by id")
    @MethodSource("bookProvider")
    fun `should return correct book by id`(expectedBook: Book) {
        val actualBook = bookRepository.findById(expectedBook.id)
        assertThat(actualBook)
            .isNotNull()
            .isEqualTo(expectedBook)
    }

    @Test
    fun `should save new book`() {
        val expectedBook =
            Book(
                id = 0L,
                title = "Androids in the Fog",
                author = Author(id = 4L, fullName = "Philip K. Dick"),
                genres =
                    mutableListOf(
                        Genre(id = 2L, name = "Science Fiction"),
                        Genre(id = 3L, name = "Detective"),
                    ),
            )
        val returnedBook = bookRepository.save(expectedBook)
        assertThat(returnedBook)
            .isNotNull()
            .matches { it.id > 0 }
            .usingRecursiveComparison()
            .ignoringExpectedNullFields()
            .isEqualTo(expectedBook.copy(id = returnedBook.id))

        assertThat(bookRepository.findById(returnedBook.id))
            .isNotNull()
            .isEqualTo(returnedBook)
    }

    @Test
    fun `should save updated book`() {
        val bookId = 4L
        val bookWithChanges =
            Book(
                id = bookId,
                title = "Androids in the Fog",
                author = Author(id = 4L, fullName = "Philip K. Dick"),
                genres =
                    mutableListOf(
                        Genre(id = 2L, name = "Science Fiction"),
                        Genre(id = 3L, name = "Detective"),
                    ),
            )
        val returnedBookBeforeUpdate = bookRepository.findById(bookId)
        val returnedBookAfterUpdate = bookRepository.save(bookWithChanges)

        assertThat(returnedBookBeforeUpdate)
            .isNotNull()
            .isNotEqualTo(bookWithChanges)

        assertThat(returnedBookAfterUpdate)
            .isNotNull()
            .matches { it.id > 0 }
            .usingRecursiveComparison()
            .ignoringExpectedNullFields()
            .isEqualTo(bookWithChanges)

        assertThat(bookRepository.findById(bookId))
            .isNotNull()
            .isEqualTo(returnedBookAfterUpdate)
    }

    @Test
    fun `should delete book`() {
        val bookId = 5L
        val returnedBookBeforeDelete = bookRepository.findById(bookId)
        bookRepository.deleteById(bookId)
        val returnedBookAfterDelete = bookRepository.findById(bookId)
        assertThat(returnedBookBeforeDelete).isNotNull()
        assertThat(returnedBookAfterDelete).isNull()
    }

    companion object {
        fun getDbAuthors(): List<Author> = listOf(Author(4L, "Philip K. Dick"), Author(5L, "Arthur Conan Doyle"))

        fun getDbGenres(): List<Genre> =
            listOf(Genre(2L, "Science Fiction"), Genre(3L, "Detective"), Genre(5L, "Mystery"), Genre(6L, "Cyberpunk"))

        fun getDbBooks(
            authors: List<Author>,
            genres: List<Genre>,
        ): List<Book> =
            listOf(
                Book(
                    id = 4L,
                    title = "Do Androids Dream of Electric Sheep?",
                    author = authors[0],
                    genres = mutableListOf(genres[0], genres[3]),
                ),
                Book(
                    id = 5L,
                    title = "The Hound of the Baskervilles",
                    author = authors[1],
                    genres = mutableListOf(genres[1], genres[2]),
                ),
            )

        @JvmStatic
        fun bookProvider() = getDbBooks(getDbAuthors(), getDbGenres())
    }
}
