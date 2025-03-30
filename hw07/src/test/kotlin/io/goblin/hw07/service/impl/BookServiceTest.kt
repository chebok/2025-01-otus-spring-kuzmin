package io.goblin.hw07.service.impl

import io.goblin.hw07.mapper.toDto
import io.goblin.hw07.model.Book
import io.goblin.hw07.service.BookService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(BookServiceImpl::class)
class BookServiceTest {
    @Autowired
    private lateinit var service: BookService

    @Autowired
    private lateinit var em: TestEntityManager

    @Test
    fun `should return correct book list`() {
        val expected = bookIds.map { em.find(Book::class.java, it) }.map { it.toDto() }
        val actual = service.findAll()
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }

    @ParameterizedTest(name = "should return book by id = {0}")
    @ValueSource(longs = [1L, 2L, 3L])
    fun `should return book by id`(bookId: Long) {
        val expected = em.find(Book::class.java, bookId).toDto()
        val actual = service.findById(bookId)
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `should save new book`() {
        val authorId = 1L
        val genresIds = setOf(2L, 3L)
        val title = "New Epic Book"

        val result = service.insert(title, authorId, genresIds)

        assertThat(result.id).isNotNull()

        val persisted = em.find(Book::class.java, result.id)
        assertThat(persisted.title).isEqualTo(title)
        assertThat(persisted.author.id).isEqualTo(authorId)
        assertThat(persisted.genres.map { it.id }.toSet()).isEqualTo(genresIds)
    }

    @Test
    fun `should update existing book`() {
        val bookId = 1L
        val newTitle = "Updated Title"
        val authorId = 1L
        val genresIds = setOf(2L, 5L)

        val result = service.update(bookId, newTitle, authorId, genresIds)

        assertThat(result.id).isEqualTo(bookId)
        assertThat(result.title).isEqualTo(newTitle)

        val persisted = em.find(Book::class.java, bookId)
        assertThat(persisted.title).isEqualTo(newTitle)
        assertThat(persisted.genres.map { it.id }.toSet()).isEqualTo(genresIds)
    }

    @Test
    fun `should delete book`() {
        val bookId = 1L
        assertThat(em.find(Book::class.java, bookId)).isNotNull()

        service.deleteById(bookId)

        assertThat(em.find(Book::class.java, bookId)).isNull()
    }

    companion object {
        private val bookIds = listOf(1L, 2L, 3L)
    }
}
