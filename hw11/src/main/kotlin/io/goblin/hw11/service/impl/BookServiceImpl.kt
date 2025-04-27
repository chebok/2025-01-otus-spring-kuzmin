package io.goblin.hw11.service.impl

import io.goblin.hw11.dto.BookDto
import io.goblin.hw11.exceptions.EntityNotFoundException
import io.goblin.hw11.mapper.toDto
import io.goblin.hw11.model.Book
import io.goblin.hw11.model.BookWithDetails
import io.goblin.hw11.persistence.repository.*
import io.goblin.hw11.service.BookService
import io.goblin.hw11.service.command.CreateBookCommand
import io.goblin.hw11.service.command.UpdateBookCommand
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository,
    private val bookCustomRepository: BookCustomRepository,
    private val bookGenreRepository: BookGenreRepository,
) : BookService {
    override suspend fun findById(id: Long): BookDto =
        bookCustomRepository.findDetailedById(id)?.toDto() ?: throw EntityNotFoundException("Book with id $id not found")

    override suspend fun findAll(): List<BookDto> = bookCustomRepository.findAllDetailed().toList().map { it.toDto() }

    override suspend fun create(command: CreateBookCommand): BookDto {
        val(title, authorId, genreIds) = command

        if (genreIds.isEmpty()) {
            throw IllegalArgumentException("Genre ids must not be empty")
        }

        val author =
            authorRepository.findById(authorId)
                ?: throw EntityNotFoundException("Author with id $authorId not found")

        val genres = genreRepository.findAllById(genreIds).toList()
        if (genres.size != genreIds.size) {
            throw EntityNotFoundException("One or more genres with ids $genreIds not found")
        }

        val book = bookRepository.save(Book(title = title, authorId = author.id!!))

        bookGenreRepository.insertAll(book.id!!, genreIds)

        return BookWithDetails(
            id = book.id!!,
            title = book.title,
            authorId = author.id,
            authorFullName = author.fullName,
            genres = genres.map { it.id!! to it.name },
        ).toDto()
    }

    override suspend fun update(command: UpdateBookCommand): BookDto {
        val(id, title, authorId, genreIds) = command

        if (genreIds.isEmpty()) {
            throw IllegalArgumentException("Genre ids must not be empty")
        }

        val author =
            authorRepository.findById(authorId)
                ?: throw EntityNotFoundException("Author with id $authorId not found")

        val genres = genreRepository.findAllById(genreIds).toList()
        if (genres.size != genreIds.size) {
            throw EntityNotFoundException("Some genres from $genreIds were not found")
        }

        val book =
            bookRepository.findById(id)
                ?: throw EntityNotFoundException("Book with id $id not found")

        val updated = book.copy(title = title, authorId = authorId)
        bookRepository.save(updated)

        bookGenreRepository.deleteAllByBookId(id)
        bookGenreRepository.insertAll(id, genreIds)

        return BookWithDetails(
            id = updated.id!!,
            title = updated.title,
            authorId = author.id!!,
            authorFullName = author.fullName,
            genres = genres.map { it.id!! to it.name },
        ).toDto()
    }

    override suspend fun deleteById(id: Long) {
        bookGenreRepository.deleteAllByBookId(id)
        bookRepository.deleteById(id)
    }
}
