package io.goblin.hw11.service

import io.goblin.hw11.dto.BookCommentDto
import io.goblin.hw11.service.command.CreateBookCommentCommand
import io.goblin.hw11.service.command.UpdateBookCommentCommand

interface BookCommentService {
    suspend fun findById(id: Long): BookCommentDto

    suspend fun findByBookId(bookId: Long): List<BookCommentDto>

    suspend fun create(command: CreateBookCommentCommand): BookCommentDto

    suspend fun update(command: UpdateBookCommentCommand): BookCommentDto

    suspend fun deleteById(id: Long)
}
