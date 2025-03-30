package io.goblin.hw07.mapper

import io.goblin.hw07.dto.BookCommentDto
import io.goblin.hw07.model.BookComment

fun BookComment.toDto(): BookCommentDto =
    BookCommentDto(
        id = requireNotNull(id) { "BookComment.id must not be null when mapping to DTO" },
        text = text,
        bookId = bookId,
    )
