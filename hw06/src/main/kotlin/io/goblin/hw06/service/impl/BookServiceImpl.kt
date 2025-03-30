package io.goblin.hw06.service.impl

import io.goblin.hw06.dto.BookDto
import io.goblin.hw06.exceptions.EntityNotFoundException
import io.goblin.hw06.mapper.toDto
import io.goblin.hw06.model.Book
import io.goblin.hw06.persistence.repository.AuthorRepository
import io.goblin.hw06.persistence.repository.BookRepository
import io.goblin.hw06.persistence.repository.GenreRepository
import io.goblin.hw06.service.BookService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookServiceImpl(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository,
) : BookService {
    @Transactional(readOnly = true)
    override fun findById(id: Long): BookDto? = bookRepository.findById(id)?.toDto()

    @Transactional(readOnly = true)
    override fun findAll(): List<BookDto> = bookRepository.findAll().map { it.toDto() }

    @Transactional
    override fun insert(
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): BookDto = save(id = null, title, authorId, genresIds).toDto()

    @Transactional
    override fun update(
        id: Long,
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): BookDto = save(id, title, authorId, genresIds).toDto()

    @Transactional
    override fun deleteById(id: Long) {
        bookRepository.deleteById(id)
    }

    private fun save(
        id: Long?,
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): Book {
        if (genresIds.isEmpty()) {
            throw IllegalArgumentException("Genres ids must not be empty")
        }
        val author =
            authorRepository.findById(authorId)
                ?: throw EntityNotFoundException("Author with id $authorId not found")
        val genres = genreRepository.findAllByIds(genresIds)
        if (genres.isEmpty() || genresIds.size != genres.size) {
            throw EntityNotFoundException("One or all genres with ids $genresIds not found")
        }
        val book = Book(id, title, author, genres)
        return bookRepository.save(book)
    }
}
