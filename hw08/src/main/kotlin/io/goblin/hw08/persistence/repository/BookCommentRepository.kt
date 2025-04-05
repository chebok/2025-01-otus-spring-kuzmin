package io.goblin.hw08.persistence.repository

import io.goblin.hw08.model.BookComment
import org.springframework.data.mongodb.repository.MongoRepository

interface BookCommentRepository : MongoRepository<BookComment, String> {
    fun findByBookId(bookId: String): List<BookComment>

    fun deleteByBookId(bookId: String)
}
