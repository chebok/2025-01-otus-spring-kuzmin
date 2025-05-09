package io.goblin.hw11.service.impl

import io.goblin.hw11.dto.BookDto
import io.goblin.hw11.exceptions.EntityNotFoundException
import io.goblin.hw11.mapper.toDto
import io.goblin.hw11.model.BookWithDetails
import io.goblin.hw11.persistence.repository.*
import io.goblin.hw11.persistence.request.InsertBookDbRequest
import io.goblin.hw11.persistence.request.UpdateBookDbRequest
import io.goblin.hw11.service.BookService
import io.goblin.hw11.service.command.CreateBookCommand
import io.goblin.hw11.service.command.UpdateBookCommand
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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

    override suspend fun create(command: CreateBookCommand): BookDto =
        coroutineScope {
            val(title, authorId, genreIds) = command

            if (genreIds.isEmpty()) {
                throw IllegalArgumentException("Genre ids must not be empty")
            }

            val authorDeferred = async { authorRepository.findById(authorId) }
            val genresDeferred = async { genreRepository.findAllById(genreIds).toList() }

            val author =
                authorDeferred.await()
                    ?: throw EntityNotFoundException("Author with id $authorId not found")

            val genres = genresDeferred.await()
            if (genres.size != genreIds.size) {
                throw EntityNotFoundException("One or more genres with ids $genreIds not found")
            }

            val book =
                bookCustomRepository.insertBookWithGenres(
                    InsertBookDbRequest(
                        title = title,
                        authorId = authorId,
                        genreIds = genreIds,
                    ),
                )

            BookWithDetails(
                id = book.id!!,
                title = book.title,
                authorId = authorId,
                authorFullName = author.fullName,
                genres = genres.map { it.id!! to it.name },
            ).toDto()
        }

    override suspend fun update(command: UpdateBookCommand): BookDto =
        coroutineScope {
            val(id, title, authorId, genreIds) = command

            if (genreIds.isEmpty()) {
                throw IllegalArgumentException("Genre ids must not be empty")
            }

            val authorDeferred = async { authorRepository.findById(authorId) }
            val genresDeferred = async { genreRepository.findAllById(genreIds).toList() }
            val bookExists = async { bookRepository.existsById(id) }

            val author =
                authorDeferred.await()
                    ?: throw EntityNotFoundException("Author with id $authorId not found")

            val genres = genresDeferred.await()
            if (genres.size != genreIds.size) {
                throw EntityNotFoundException("One or more genres with ids $genreIds not found")
            }

            if (!bookExists.await()) throw EntityNotFoundException("Book with id $id not found")

            bookCustomRepository.updateBookWithGenres(
                UpdateBookDbRequest(
                    id = id,
                    title = title,
                    authorId = authorId,
                    genreIds = genreIds,
                ),
            )

            BookWithDetails(
                id = id,
                title = title,
                authorId = authorId,
                authorFullName = author.fullName,
                genres = genres.map { it.id!! to it.name },
            ).toDto()
        }

    override suspend fun deleteById(id: Long) {
        bookGenreRepository.deleteAllByBookId(id)
        bookRepository.deleteById(id)
    }
}
