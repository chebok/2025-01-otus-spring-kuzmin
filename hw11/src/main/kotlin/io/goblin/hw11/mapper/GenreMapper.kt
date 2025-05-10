package io.goblin.hw11.mapper

import io.goblin.hw11.dto.GenreDto
import io.goblin.hw11.model.Genre

fun Genre.toDto(): GenreDto =
    GenreDto(
        id = requireNotNull(id) { "Genre.id must not be null when mapping to DTO" },
        name = name,
    )
