package io.goblin.hw06.converters

import io.goblin.hw06.model.BookComment
import org.springframework.stereotype.Component

@Component
class BookCommentConverter {
    fun commentToString(bookComment: BookComment): String = "Id: ${bookComment.id}, Text: ${bookComment.text}"
}
