package io.goblin.hw10.service.command

data class CreateBookCommand(
    val title: String,
    val authorId: Long,
    val genreIds: Set<Long>,
)
