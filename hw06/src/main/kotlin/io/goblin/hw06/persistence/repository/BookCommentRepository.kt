package io.goblin.hw06.persistence.repository

import io.goblin.hw06.model.BookComment

interface BookCommentRepository {
    fun findById(id: Long): BookComment?

    fun findByBookId(bookId: Long): List<BookComment>

    fun save(bookComment: BookComment): BookComment

    fun deleteById(id: Long)
}
