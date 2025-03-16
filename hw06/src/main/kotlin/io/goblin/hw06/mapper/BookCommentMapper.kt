package io.goblin.hw06.mapper

import io.goblin.hw06.model.BookComment
import io.goblin.hw06.persistence.entity.BookCommentEntity

fun BookCommentEntity.toDomain(): BookComment =
    BookComment(
        id = id ?: throw IllegalStateException("ID cannot be null"),
        text = text,
        bookId = bookId,
    )

fun BookComment.toJpaEntity(): BookCommentEntity =
    BookCommentEntity(
        id = if (id == 0L) null else id,
        text = text,
        bookId = bookId,
    )
