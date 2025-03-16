package io.goblin.hw06.service

import io.goblin.hw06.model.BookComment

interface BookCommentService {
    fun findById(id: Long): BookComment?

    fun findByBookId(bookId: Long): List<BookComment>

    fun insert(
        text: String,
        bookId: Long,
    ): BookComment

    fun update(
        id: Long,
        text: String,
        bookId: Long,
    ): BookComment

    fun deleteById(id: Long)
}
