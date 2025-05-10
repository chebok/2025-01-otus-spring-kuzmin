package io.goblin.hw11.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("books_genres")
data class BookGenre(
    @Column("book_id")
    val bookId: Long,
    @Column("genre_id")
    val genreId: Long,
)
