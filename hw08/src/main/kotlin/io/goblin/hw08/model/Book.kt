package io.goblin.hw08.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("books")
class Book(
    @Id
    var id: String? = null,
    var title: String,
    @DBRef(lazy = false)
    var author: Author,
    var genres: List<Genre> = emptyList(),
) {
    data class Genre(
        val id: String,
        val name: String,
    )
}

fun Iterable<Genre>.toEmbedded(): List<Book.Genre> =
    map { genre ->
        Book.Genre(
            id = requireNotNull(genre.id) { "Genre.id must not be null when embedding into Book" },
            name = genre.name,
        )
    }
