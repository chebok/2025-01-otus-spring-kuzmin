package io.goblin.hw06.service.impl

import io.goblin.hw06.exceptions.EntityNotFoundException
import io.goblin.hw06.model.BookComment
import io.goblin.hw06.persistence.repository.BookCommentRepository
import io.goblin.hw06.persistence.repository.BookRepository
import io.goblin.hw06.service.BookCommentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookCommentServiceImpl(
    private val bookCommentRepository: BookCommentRepository,
    private val bookRepository: BookRepository,
) : BookCommentService {
    @Transactional(readOnly = true)
    override fun findById(id: Long): BookComment? = bookCommentRepository.findById(id)

    @Transactional(readOnly = true)
    override fun findByBookId(bookId: Long): List<BookComment> =
        bookRepository.findById(bookId)?.let {
            bookCommentRepository.findByBookId(bookId)
        } ?: throw EntityNotFoundException("Book with id $bookId not found")

    @Transactional
    override fun insert(
        text: String,
        bookId: Long,
    ): BookComment = save(0L, text, bookId)

    @Transactional
    override fun update(
        id: Long,
        text: String,
        bookId: Long,
    ): BookComment = save(id, text, bookId)

    @Transactional
    override fun deleteById(id: Long) {
        bookCommentRepository.deleteById(id)
    }

    private fun save(
        id: Long,
        text: String,
        bookId: Long,
    ): BookComment {
        val book = bookRepository.findById(bookId) ?: throw EntityNotFoundException("Book with id $bookId not found")
        val bookComment = BookComment(id, text, book.id)
        return bookCommentRepository.save(bookComment)
    }
}
