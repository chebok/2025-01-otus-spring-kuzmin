package io.goblin.hw08.service.impl

import io.goblin.hw08.dto.BookDto
import io.goblin.hw08.exceptions.EntityNotFoundException
import io.goblin.hw08.mapper.toDto
import io.goblin.hw08.model.Book
import io.goblin.hw08.model.toEmbedded
import io.goblin.hw08.persistence.repository.AuthorRepository
import io.goblin.hw08.persistence.repository.BookRepository
import io.goblin.hw08.persistence.repository.GenreRepository
import io.goblin.hw08.service.BookService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository,
) : BookService {
    override fun findById(id: String): BookDto? = bookRepository.findByIdOrNull(id)?.toDto()

    override fun findAll(): List<BookDto> = bookRepository.findAll().map { it.toDto() }

    override fun create(
        title: String,
        authorId: String,
        genresIds: Set<String>,
    ): BookDto {
        if (genresIds.isEmpty()) {
            throw IllegalArgumentException("Genres ids must not be empty")
        }
        val author =
            authorRepository.findById(authorId).orElseThrow { EntityNotFoundException("Author with id $authorId not found") }
        val genres = genreRepository.findAllById(genresIds)
        if (genres.isEmpty() || genresIds.size != genres.size) {
            throw EntityNotFoundException("One or all genres with ids $genresIds not found")
        }
        val book = Book(title = title, author = author, genres = genres.toEmbedded())
        return bookRepository.save(book).toDto()
    }

    override fun update(
        id: String,
        title: String,
        authorId: String,
        genresIds: Set<String>,
    ): BookDto {
        val book = bookRepository.findById(id).orElseThrow { EntityNotFoundException("Book with id $id not found") }
        if (genresIds.isEmpty()) {
            throw IllegalArgumentException("Genres ids must not be empty")
        }
        val author =
            authorRepository.findById(authorId).orElseThrow { EntityNotFoundException("Author with id $authorId not found") }
        val genres = genreRepository.findAllById(genresIds)
        if (genres.isEmpty() || genresIds.size != genres.size) {
            throw EntityNotFoundException("One or all genres with ids $genresIds not found")
        }
        book.apply {
            this.title = title
            this.author = author
            this.genres = genres.toEmbedded()
        }
        return bookRepository.save(book).toDto()
    }

    override fun deleteById(id: String) {
        bookRepository.deleteById(id)
    }
}
