package io.goblin.hw08.listeners

import io.goblin.hw08.events.BookDeletedEvent
import io.goblin.hw08.persistence.repository.BookCommentRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class BookDeletedListener(
    private val commentRepository: BookCommentRepository,
) {
    @EventListener
    fun handleBookDeleted(event: BookDeletedEvent) {
        commentRepository.deleteByBookId(event.bookId)
    }
}
