package io.goblin.hw10.persistence.repository

import io.goblin.hw10.model.BookComment
import org.springframework.data.jpa.repository.JpaRepository

interface BookCommentRepository : JpaRepository<BookComment, Long> {
    fun findByBookId(bookId: Long): List<BookComment>
}
