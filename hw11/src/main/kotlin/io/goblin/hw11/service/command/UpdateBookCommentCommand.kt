package io.goblin.hw11.service.command

data class UpdateBookCommentCommand(
    val id: Long,
    val text: String,
)
