package io.goblin.hw07.mapper

import io.goblin.hw07.dto.BookDto
import io.goblin.hw07.model.Book

fun Book.toDto(): BookDto =
    BookDto(
        id = requireNotNull(id) { "Book.id must not be null when mapping to DTO" },
        title = title,
        author = author.toDto(),
        genres = genres.map { it.toDto() },
    )
