package io.goblin.hw08.mapper

import io.goblin.hw08.dto.GenreDto
import io.goblin.hw08.model.Genre

fun Genre.toDto(): GenreDto =
    GenreDto(
        id = requireNotNull(id) { "Genre.id must not be null when mapping to DTO" },
        name = name,
    )
