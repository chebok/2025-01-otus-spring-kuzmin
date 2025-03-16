package io.goblin.hw06.mapper

import io.goblin.hw06.model.Author
import io.goblin.hw06.persistence.entity.AuthorEntity

fun AuthorEntity.toDomain(): Author =
    Author(
        id = id ?: throw IllegalStateException("ID cannot be null"),
        fullName = fullName,
    )

fun Author.toJpaEntity(): AuthorEntity =
    AuthorEntity(
        id = if (id == 0L) null else id,
        fullName = fullName,
    )
