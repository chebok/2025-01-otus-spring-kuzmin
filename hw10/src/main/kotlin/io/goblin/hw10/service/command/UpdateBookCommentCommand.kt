package io.goblin.hw10.service.command

data class UpdateBookCommentCommand(
    val id: Long,
    val text: String,
)
