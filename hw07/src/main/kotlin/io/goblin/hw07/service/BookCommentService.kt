package io.goblin.hw07.service

import io.goblin.hw07.dto.BookCommentDto

interface BookCommentService {
    fun findById(id: Long): BookCommentDto?

    fun findByBookId(bookId: Long): List<BookCommentDto>

    fun insert(
        text: String,
        bookId: Long,
    ): BookCommentDto

    fun update(
        id: Long,
        text: String,
        bookId: Long,
    ): BookCommentDto

    fun deleteById(id: Long)
}
