package io.goblin.hw07.service.impl

import io.goblin.hw07.dto.BookDto
import io.goblin.hw07.exceptions.EntityNotFoundException
import io.goblin.hw07.mapper.toDto
import io.goblin.hw07.model.Book
import io.goblin.hw07.persistence.repository.AuthorRepository
import io.goblin.hw07.persistence.repository.BookRepository
import io.goblin.hw07.persistence.repository.GenreRepository
import io.goblin.hw07.service.BookService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class BookServiceImpl(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository,
) : BookService {
    @Transactional(readOnly = true)
    override fun findById(id: Long): BookDto? = bookRepository.findById(id).getOrNull()?.toDto()

    @Transactional(readOnly = true)
    override fun findAll(): List<BookDto> = bookRepository.findAll().map { it.toDto() }

    @Transactional
    override fun insert(
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): BookDto {
        if (genresIds.isEmpty()) {
            throw IllegalArgumentException("Genres ids must not be empty")
        }
        val author =
            authorRepository.findById(authorId).orElseThrow { EntityNotFoundException("Author with id $authorId not found") }
        val genres = genreRepository.findAllByIds(genresIds)
        if (genres.isEmpty() || genresIds.size != genres.size) {
            throw EntityNotFoundException("One or all genres with ids $genresIds not found")
        }
        val book = Book(title = title, author = author, genres = genres)
        return bookRepository.save(book).toDto()
    }

    @Transactional
    override fun update(
        id: Long,
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): BookDto {
        val book = bookRepository.findById(id).orElseThrow { EntityNotFoundException("Book with id $id not found") }
        if (genresIds.isEmpty()) {
            throw IllegalArgumentException("Genres ids must not be empty")
        }
        val author =
            authorRepository.findById(authorId).orElseThrow { EntityNotFoundException("Author with id $authorId not found") }
        val genres = genreRepository.findAllByIds(genresIds)
        if (genres.isEmpty() || genresIds.size != genres.size) {
            throw EntityNotFoundException("One or all genres with ids $genresIds not found")
        }
        book.apply {
            this.title = title
            this.author = author
            this.genres = genres
        }
        return bookRepository.save(book).toDto()
    }

    @Transactional
    override fun deleteById(id: Long) {
        bookRepository.deleteById(id)
    }
}
