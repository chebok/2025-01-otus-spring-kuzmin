package io.goblin.hw08.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("genres")
class Genre(
    @Id
    var id: String? = null,
    val name: String,
)
