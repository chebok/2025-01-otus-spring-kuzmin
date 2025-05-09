package io.goblin.hw07.service.impl

import io.goblin.hw07.dto.BookCommentDto
import io.goblin.hw07.exceptions.EntityNotFoundException
import io.goblin.hw07.mapper.toDto
import io.goblin.hw07.model.BookComment
import io.goblin.hw07.persistence.repository.BookCommentRepository
import io.goblin.hw07.persistence.repository.BookRepository
import io.goblin.hw07.service.BookCommentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class BookCommentServiceImpl(
    private val bookCommentRepository: BookCommentRepository,
    private val bookRepository: BookRepository,
) : BookCommentService {
    @Transactional(readOnly = true)
    override fun findById(id: Long): BookCommentDto? = bookCommentRepository.findById(id).getOrNull()?.toDto()

    @Transactional(readOnly = true)
    override fun findByBookId(bookId: Long): List<BookCommentDto> {
        bookRepository.findById(bookId).orElseThrow {
            EntityNotFoundException("Book with id $bookId not found")
        }
        return bookCommentRepository.findByBookId(bookId).map { it.toDto() }
    }

    @Transactional
    override fun insert(
        text: String,
        bookId: Long,
    ): BookCommentDto {
        val book = bookRepository.findById(bookId).orElseThrow { EntityNotFoundException("Book with id $bookId not found") }
        val bookComment = BookComment(text = text, bookId = book.id!!)
        return bookCommentRepository.save(bookComment).toDto()
    }

    @Transactional
    override fun update(
        id: Long,
        text: String,
    ): BookCommentDto {
        val bookComment = bookCommentRepository.findById(id).orElseThrow { EntityNotFoundException("Comment with id $id not found") }
        bookComment.apply {
            this.text = text
        }
        return bookCommentRepository.save(bookComment).toDto()
    }

    @Transactional
    override fun deleteById(id: Long) {
        bookCommentRepository.deleteById(id)
    }
}
