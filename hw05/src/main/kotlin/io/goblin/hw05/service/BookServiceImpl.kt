package io.goblin.hw05.service

import io.goblin.hw05.exceptions.EntityNotFoundException
import io.goblin.hw05.model.Book
import io.goblin.hw05.repository.AuthorRepository
import io.goblin.hw05.repository.BookRepository
import io.goblin.hw05.repository.GenreRepository
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val bookRepository: BookRepository,
) : BookService {
    override fun findById(id: Long): Book? = bookRepository.findById(id)

    override fun findAll(): List<Book> = bookRepository.findAll()

    override fun insert(
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): Book = save(0, title, authorId, genresIds)

    override fun update(
        id: Long,
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): Book = save(id, title, authorId, genresIds)

    override fun deleteById(id: Long) {
        bookRepository.deleteById(id)
    }

    private fun save(
        id: Long,
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
        val book = Book(id, title, author, genres.toMutableList())
        return bookRepository.save(book)
    }
}
