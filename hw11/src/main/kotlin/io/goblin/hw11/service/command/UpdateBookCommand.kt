package io.goblin.hw11.service.command

data class UpdateBookCommand(
    val id: Long,
    val title: String,
    val authorId: Long,
    val genreIds: Set<Long>,
)
