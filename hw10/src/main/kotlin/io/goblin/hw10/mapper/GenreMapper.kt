package io.goblin.hw10.mapper

import io.goblin.hw10.dto.GenreDto
import io.goblin.hw10.model.Genre

fun Genre.toDto(): GenreDto =
    GenreDto(
        id = requireNotNull(id) { "Genre.id must not be null when mapping to DTO" },
        name = name,
    )
