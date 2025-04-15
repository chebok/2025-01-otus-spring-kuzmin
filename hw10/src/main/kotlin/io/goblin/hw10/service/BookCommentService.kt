package io.goblin.hw10.service

import io.goblin.hw10.dto.BookCommentDto

interface BookCommentService {
    fun findById(id: Long): BookCommentDto

    fun findByBookId(bookId: Long): List<BookCommentDto>

    fun create(
        text: String,
        bookId: Long,
    ): BookCommentDto

    fun update(
        id: Long,
        text: String,
    ): BookCommentDto

    fun deleteById(id: Long)
}
