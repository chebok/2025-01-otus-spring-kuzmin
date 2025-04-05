package io.goblin.hw08.service

import io.goblin.hw08.dto.BookCommentDto

interface BookCommentService {
    fun findById(id: String): BookCommentDto?

    fun findByBookId(bookId: String): List<BookCommentDto>

    fun insert(
        text: String,
        bookId: String,
    ): BookCommentDto

    fun update(
        id: String,
        text: String,
    ): BookCommentDto

    fun deleteById(id: String)
}
