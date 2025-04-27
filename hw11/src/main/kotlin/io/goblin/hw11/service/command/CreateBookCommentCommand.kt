package io.goblin.hw11.service.command

data class CreateBookCommentCommand(
    val text: String,
    val bookId: Long,
)
