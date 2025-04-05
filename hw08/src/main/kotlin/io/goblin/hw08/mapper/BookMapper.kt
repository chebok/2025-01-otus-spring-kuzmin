package io.goblin.hw08.mapper

import io.goblin.hw08.dto.BookDto
import io.goblin.hw08.dto.GenreDto
import io.goblin.hw08.model.Book

fun Book.toDto(): BookDto =
    BookDto(
        id = requireNotNull(id) { "Book.id must not be null when mapping to DTO" },
        title = title,
        author = author.toDto(),
        genres = genres.map { (id, name) -> GenreDto(id, name) },
    )
