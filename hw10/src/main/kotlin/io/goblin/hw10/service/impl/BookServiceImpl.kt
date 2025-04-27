package io.goblin.hw10.service.impl

import io.goblin.hw10.dto.BookDto
import io.goblin.hw10.exceptions.EntityNotFoundException
import io.goblin.hw10.mapper.toDto
import io.goblin.hw10.model.Book
import io.goblin.hw10.persistence.repository.AuthorRepository
import io.goblin.hw10.persistence.repository.BookRepository
import io.goblin.hw10.persistence.repository.GenreRepository
import io.goblin.hw10.service.BookService
import io.goblin.hw10.service.command.CreateBookCommand
import io.goblin.hw10.service.command.UpdateBookCommand
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
    override fun findById(id: Long): BookDto =
        bookRepository.findById(id).getOrNull()?.toDto() ?: throw EntityNotFoundException("Book with id $id not found")

    @Transactional(readOnly = true)
    override fun findAll(): List<BookDto> = bookRepository.findAll().map { it.toDto() }

    @Transactional
    override fun create(command: CreateBookCommand): BookDto {
        val(title, authorId, genresIds) = command
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
    override fun update(command: UpdateBookCommand): BookDto {
        val(id, title, authorId, genresIds) = command
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
