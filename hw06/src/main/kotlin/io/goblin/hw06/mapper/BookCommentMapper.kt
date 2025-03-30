package io.goblin.hw06.mapper

import io.goblin.hw06.dto.BookCommentDto
import io.goblin.hw06.model.BookComment

fun BookComment.toDto(): BookCommentDto =
    BookCommentDto(
        id = requireNotNull(id) { "BookComment.id must not be null when mapping to DTO" },
        text = text,
        bookId = bookId,
    )
