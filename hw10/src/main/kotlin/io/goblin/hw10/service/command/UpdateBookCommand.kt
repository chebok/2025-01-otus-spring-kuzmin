package io.goblin.hw10.service.command

data class UpdateBookCommand(
    val id: Long,
    val title: String,
    val authorId: Long,
    val genreIds: Set<Long>,
)
