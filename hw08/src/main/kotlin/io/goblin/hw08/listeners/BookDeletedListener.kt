package io.goblin.hw08.listeners

import io.goblin.hw08.model.Book
import io.goblin.hw08.persistence.repository.BookCommentRepository
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent
import org.springframework.stereotype.Component

@Component
class BookMongoEventListener(
    private val commentRepository: BookCommentRepository,
) : AbstractMongoEventListener<Book>() {
    override fun onAfterDelete(event: AfterDeleteEvent<Book>) {
        val source = event.source
        val bookId = source["_id"]?.toString()

        if (bookId != null) {
            println(">>> [EVENT] Deleting comments for book $bookId")
            commentRepository.deleteByBookId(bookId)
        }
    }
}
