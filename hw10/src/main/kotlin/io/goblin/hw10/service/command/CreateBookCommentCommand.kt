package io.goblin.hw10.service.command

data class CreateBookCommentCommand(
    val text: String,
    val bookId: Long,
)
