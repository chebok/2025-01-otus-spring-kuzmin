package io.goblin.hw11.service.impl

import io.goblin.hw11.dto.BookCommentDto
import io.goblin.hw11.exceptions.EntityNotFoundException
import io.goblin.hw11.mapper.toDto
import io.goblin.hw11.model.BookComment
import io.goblin.hw11.persistence.repository.BookCommentRepository
import io.goblin.hw11.persistence.repository.BookRepository
import io.goblin.hw11.service.BookCommentService
import io.goblin.hw11.service.command.CreateBookCommentCommand
import io.goblin.hw11.service.command.UpdateBookCommentCommand
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class BookCommentServiceImpl(
    private val bookCommentRepository: BookCommentRepository,
    private val bookRepository: BookRepository,
) : BookCommentService {
    override suspend fun findById(id: Long): BookCommentDto =
        bookCommentRepository.findById(id)?.toDto()
            ?: throw EntityNotFoundException("Comment with id $id not found")

    override suspend fun findByBookId(bookId: Long): List<BookCommentDto> {
        val exists = bookRepository.existsById(bookId)
        if (!exists) throw EntityNotFoundException("Book with id $bookId not found")

        return bookCommentRepository.findByBookId(bookId).toList().map { it.toDto() }
    }

    override suspend fun create(command: CreateBookCommentCommand): BookCommentDto {
        val (text, bookId) = command
        val exists = bookRepository.existsById(bookId)
        if (!exists) throw EntityNotFoundException("Book with id $bookId not found")

        val bookComment = BookComment(text = text, bookId = bookId)
        return bookCommentRepository.save(bookComment).toDto()
    }

    override suspend fun update(command: UpdateBookCommentCommand): BookCommentDto {
        val (id, text) = command
        val bookComment =
            bookCommentRepository.findById(id)
                ?: throw EntityNotFoundException("Comment with id $id not found")

        val updated = bookComment.copy(text = text)
        return bookCommentRepository.save(updated).toDto()
    }

    override suspend fun deleteById(id: Long) {
        bookCommentRepository.deleteById(id)
    }
}
