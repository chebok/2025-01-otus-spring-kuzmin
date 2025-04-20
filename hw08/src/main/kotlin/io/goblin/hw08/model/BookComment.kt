package io.goblin.hw08.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("book_comments")
class BookComment(
    @Id
    var id: String? = null,
    var text: String,
    val bookId: String,
)
