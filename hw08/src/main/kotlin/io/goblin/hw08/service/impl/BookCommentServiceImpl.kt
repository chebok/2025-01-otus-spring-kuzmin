package io.goblin.hw08.service.impl

import io.goblin.hw08.dto.BookCommentDto
import io.goblin.hw08.exceptions.EntityNotFoundException
import io.goblin.hw08.mapper.toDto
import io.goblin.hw08.model.BookComment
import io.goblin.hw08.persistence.repository.BookCommentRepository
import io.goblin.hw08.persistence.repository.BookRepository
import io.goblin.hw08.service.BookCommentService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookCommentServiceImpl(
    private val bookCommentRepository: BookCommentRepository,
    private val bookRepository: BookRepository,
) : BookCommentService {
    override fun findById(id: String): BookCommentDto? = bookCommentRepository.findByIdOrNull(id)?.toDto()

    override fun findByBookId(bookId: String): List<BookCommentDto> {
        bookRepository.findById(bookId).orElseThrow {
            EntityNotFoundException("Book with id $bookId not found")
        }
        return bookCommentRepository.findByBookId(bookId).map { it.toDto() }
    }

    override fun create(
        text: String,
        bookId: String,
    ): BookCommentDto {
        val book = bookRepository.findById(bookId).orElseThrow { EntityNotFoundException("Book with id $bookId not found") }
        val bookComment = BookComment(text = text, bookId = book.id!!)
        return bookCommentRepository.save(bookComment).toDto()
    }

    override fun update(
        id: String,
        text: String,
    ): BookCommentDto {
        val bookComment = bookCommentRepository.findById(id).orElseThrow { EntityNotFoundException("Comment with id $id not found") }
        bookComment.apply {
            this.text = text
        }
        return bookCommentRepository.save(bookComment).toDto()
    }

    override fun deleteById(id: String) {
        bookCommentRepository.deleteById(id)
    }
}
