package io.goblin.hw11.persistence.repository

import io.goblin.hw11.model.BookComment
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BookCommentRepository : CoroutineCrudRepository<BookComment, Long> {
    fun findByBookId(bookId: Long): Flow<BookComment>
}
