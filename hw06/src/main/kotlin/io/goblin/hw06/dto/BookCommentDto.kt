package io.goblin.hw06.dto

data class BookCommentDto(
    val id: Long,
    val text: String,
    val bookId: Long,
)
