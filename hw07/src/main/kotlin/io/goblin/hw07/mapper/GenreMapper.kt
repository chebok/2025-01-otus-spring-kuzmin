package io.goblin.hw07.mapper

import io.goblin.hw07.dto.GenreDto
import io.goblin.hw07.model.Genre

fun Genre.toDto(): GenreDto =
    GenreDto(
        id = requireNotNull(id) { "Genre.id must not be null when mapping to DTO" },
        name = name,
    )
