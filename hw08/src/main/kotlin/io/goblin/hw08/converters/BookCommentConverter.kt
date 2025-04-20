package io.goblin.hw08.converters

import io.goblin.hw08.dto.BookCommentDto
import org.springframework.stereotype.Component

@Component
class BookCommentConverter {
    fun commentToString(bookComment: BookCommentDto): String = "Id: ${bookComment.id}, Text: ${bookComment.text}"
}
