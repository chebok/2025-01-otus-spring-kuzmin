package io.goblin.hw07.persistence.repository

import io.goblin.hw07.model.BookComment
import org.springframework.data.jpa.repository.JpaRepository

interface BookCommentRepository : JpaRepository<BookComment, Long> {
    fun findByBookId(bookId: Long): List<BookComment>
}
