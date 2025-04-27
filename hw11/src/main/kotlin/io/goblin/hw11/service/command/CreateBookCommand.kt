package io.goblin.hw11.service.command

data class CreateBookCommand(
    val title: String,
    val authorId: Long,
    val genreIds: Set<Long>,
)
