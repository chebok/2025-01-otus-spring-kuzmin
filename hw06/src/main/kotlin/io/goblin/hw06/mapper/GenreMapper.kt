package io.goblin.hw06.mapper

import io.goblin.hw06.dto.GenreDto
import io.goblin.hw06.model.Genre

fun Genre.toDto(): GenreDto =
    GenreDto(
        id = requireNotNull(id) { "Genre.id must not be null when mapping to DTO" },
        name = name,
    )
