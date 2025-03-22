package io.goblin.hw06.service.impl

import io.goblin.hw06.dto.BookCommentDto
import io.goblin.hw06.exceptions.EntityNotFoundException
import io.goblin.hw06.mapper.toDto
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
    override fun findById(id: Long): BookCommentDto? = bookCommentRepository.findById(id)?.toDto()

    @Transactional(readOnly = true)
    override fun findByBookId(bookId: Long): List<BookCommentDto> =
        bookRepository.findById(bookId)?.let {
            bookCommentRepository.findByBookId(bookId).map { it.toDto() }
        } ?: throw EntityNotFoundException("Book with id $bookId not found")

    @Transactional
    override fun insert(
        text: String,
        bookId: Long,
    ): BookCommentDto = save(id = null, text, bookId).toDto()

    @Transactional
    override fun update(
        id: Long,
        text: String,
        bookId: Long,
    ): BookCommentDto = save(id, text, bookId).toDto()

    @Transactional
    override fun deleteById(id: Long) {
        bookCommentRepository.deleteById(id)
    }

    private fun save(
        id: Long?,
        text: String,
        bookId: Long,
    ): BookComment {
        val book = bookRepository.findById(bookId) ?: throw EntityNotFoundException("Book with id $bookId not found")
        val bookComment = BookComment(id, text, book.id!!)
        return bookCommentRepository.save(bookComment)
    }
}
