package io.goblin.hw06.mapper

import io.goblin.hw06.model.Book
import io.goblin.hw06.persistence.entity.BookEntity

fun BookEntity.toDomain(): Book =
    Book(
        id = id ?: throw IllegalStateException("ID cannot be null"),
        title = title,
        author = author.toDomain(),
        genres = genres.map { it.toDomain() },
    )

fun Book.toJpaEntity(): BookEntity =
    BookEntity(
        id = if (id == 0L) null else id,
        title = title,
        author = author.toJpaEntity(),
        genres = genres.map { it.toJpaEntity() },
    )
