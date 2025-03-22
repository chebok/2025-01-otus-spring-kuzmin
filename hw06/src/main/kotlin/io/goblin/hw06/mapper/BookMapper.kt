package io.goblin.hw06.mapper

import io.goblin.hw06.dto.BookDto
import io.goblin.hw06.model.Book

fun Book.toDto(): BookDto =
    BookDto(
        id = requireNotNull(id) { "Book.id must not be null when mapping to DTO" },
        title = title,
        author = author.toDto(),
        genres = genres.map { it.toDto() },
    )
