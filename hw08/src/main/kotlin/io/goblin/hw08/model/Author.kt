package io.goblin.hw08.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("authors")
class Author(
    @Id
    var id: String? = null,
    val fullName: String,
)
