package io.goblin.hw11.dto

data class BookCommentDto(
    val id: Long,
    val text: String,
    val bookId: Long,
)
