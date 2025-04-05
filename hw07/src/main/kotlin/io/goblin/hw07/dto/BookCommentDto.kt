package io.goblin.hw07.dto

data class BookCommentDto(
    val id: Long,
    val text: String,
    val bookId: Long,
)
