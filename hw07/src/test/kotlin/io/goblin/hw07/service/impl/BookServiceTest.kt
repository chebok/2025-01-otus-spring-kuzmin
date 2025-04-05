package io.goblin.hw07.service.impl

import io.goblin.hw07.mapper.toDto
import io.goblin.hw07.persistence.repository.BookRepository
import io.goblin.hw07.service.BookService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@DataJpaTest
@ActiveProfiles("test")
@Import(BookServiceImpl::class)
@Sql(scripts = ["/data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BookServiceTest {
    @Autowired
    private lateinit var service: BookService

    @Autowired
    private lateinit var repository: BookRepository

    @Test
    @Transactional(propagation = Propagation.NEVER)
    fun `should return correct book list`() {
        val expected = bookIds.mapNotNull { repository.findById(it).getOrNull() }.map { it.toDto() }
        val actual = service.findAll()
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }

    @ParameterizedTest(name = "should return book by id = {0}")
    @ValueSource(longs = [1L, 2L, 3L])
    @Transactional(propagation = Propagation.NEVER)
    fun `should return book by id`(bookId: Long) {
        val expected = repository.findById(bookId).getOrNull()?.toDto()
        val actual = service.findById(bookId)
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    fun `should save new book`() {
        val authorId = 1L
        val genresIds = setOf(2L, 3L)
        val title = "New Epic Book"

        val result = service.insert(title, authorId, genresIds)

        assertThat(result.id).isNotNull()

        val persisted = repository.findById(result.id).getOrNull()?.toDto()
        assertThat(persisted?.title).isEqualTo(title)
        assertThat(persisted?.author?.id).isEqualTo(authorId)
        assertThat(persisted?.genres?.map { it.id }?.toSet()).isEqualTo(genresIds)
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    fun `should update existing book`() {
        val bookId = 1L
        val newTitle = "Updated Title"
        val authorId = 1L
        val genresIds = setOf(2L, 5L)

        val result = service.update(bookId, newTitle, authorId, genresIds)

        assertThat(result.id).isEqualTo(bookId)
        assertThat(result.title).isEqualTo(newTitle)

        val persisted = repository.findById(bookId).getOrNull()?.toDto()
        assertThat(persisted?.title).isEqualTo(newTitle)
        assertThat(persisted?.genres?.map { it.id }?.toSet()).isEqualTo(genresIds)
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    fun `should delete book`() {
        val bookId = 1L
        assertThat(repository.findById(bookId).getOrNull()).isNotNull()

        service.deleteById(bookId)

        assertThat(repository.findById(bookId).getOrNull()).isNull()
    }

    companion object {
        private val bookIds = listOf(1L, 2L, 3L)
    }
}
