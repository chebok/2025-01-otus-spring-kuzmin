package io.goblin.hw10.service

import io.goblin.hw10.dto.BookCommentDto
import io.goblin.hw10.service.command.CreateBookCommentCommand
import io.goblin.hw10.service.command.UpdateBookCommentCommand

interface BookCommentService {
    fun findById(id: Long): BookCommentDto

    fun findByBookId(bookId: Long): List<BookCommentDto>

    fun create(command: CreateBookCommentCommand): BookCommentDto

    fun update(command: UpdateBookCommentCommand): BookCommentDto

    fun deleteById(id: Long)
}
