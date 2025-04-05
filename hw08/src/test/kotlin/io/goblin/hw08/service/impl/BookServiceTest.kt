package io.goblin.hw08.service.impl

import io.goblin.hw08.mapper.toDto
import io.goblin.hw08.model.Author
import io.goblin.hw08.model.Book
import io.goblin.hw08.model.Genre
import io.goblin.hw08.service.BookService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles

@DataMongoTest
@Import(BookServiceImpl::class)
@ActiveProfiles("test")
class BookServiceTest {
    @Autowired
    private lateinit var service: BookService

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    private lateinit var testBooks: List<Book>

    @BeforeEach
    fun setup() {
        testBooks = mongoTemplate.findAll(Book::class.java).take(3)
        require(testBooks.size >= 2) { "Expected at least 2 books in test DB" }
    }

    @Test
    fun `should return correct book list`() {
        val expected = testBooks.map { it.toDto() }
        val actual = service.findAll()
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should return up to three books by id`() {
        val books = mongoTemplate.findAll(Book::class.java).take(3)

        books.forEach { book ->
            val expected = book.toDto()
            val actual = service.findById(book.id!!)
            assertThat(actual)
                .withFailMessage("Mismatch for book with id ${book.id}")
                .usingRecursiveComparison()
                .isEqualTo(expected)
        }
    }

    @Test
    fun `should save new book`() {
        val author = mongoTemplate.findAll(Author::class.java).first()
        val genres = mongoTemplate.findAll(Genre::class.java).take(2)

        val result =
            service.insert(
                title = "New Epic Book",
                authorId = requireNotNull(author.id),
                genresIds = genres.mapNotNull { it.id }.toSet(),
            )

        val persisted = mongoTemplate.findById(result.id, Book::class.java)?.toDto()
        assertThat(persisted?.title).isEqualTo("New Epic Book")
        assertThat(persisted?.author?.id).isEqualTo(author.id)
        assertThat(persisted?.genres?.map { it.id }?.toSet()).isEqualTo(genres.mapNotNull { it.id }.toSet())
    }

    @Test
    fun `should update existing book`() {
        val existingBook = testBooks.first()
        val newTitle = "Updated Title"
        val author = mongoTemplate.findAll(Author::class.java).first()
        val genres = mongoTemplate.findAll(Genre::class.java).take(2)

        val result =
            service.update(
                id = requireNotNull(existingBook.id),
                title = newTitle,
                authorId = requireNotNull(author.id),
                genresIds = genres.mapNotNull { it.id }.toSet(),
            )

        val persisted = mongoTemplate.findById(result.id, Book::class.java)?.toDto()
        assertThat(persisted?.title).isEqualTo(newTitle)
        assertThat(persisted?.genres?.map { it.id }?.toSet()).isEqualTo(genres.mapNotNull { it.id }.toSet())
    }

    @Test
    fun `should delete book`() {
        val bookId = requireNotNull(testBooks.first().id)
        assertThat(mongoTemplate.findById(bookId, Book::class.java)).isNotNull()

        service.deleteById(bookId)

        assertThat(mongoTemplate.findById(bookId, Book::class.java)).isNull()
    }
}
