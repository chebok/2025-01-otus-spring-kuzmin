package io.goblin.hw06.mapper

import io.goblin.hw06.model.Genre
import io.goblin.hw06.persistence.entity.GenreEntity

fun GenreEntity.toDomain(): Genre =
    Genre(
        id = id ?: throw IllegalStateException("ID cannot be null"),
        name = name,
    )

fun Genre.toJpaEntity(): GenreEntity =
    GenreEntity(
        id = if (id == 0L) null else id,
        name = name,
    )
