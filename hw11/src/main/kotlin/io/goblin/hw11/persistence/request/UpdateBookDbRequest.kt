package io.goblin.hw11.persistence.request

data class UpdateBookDbRequest(
    val id: Long,
    val title: String,
    val authorId: Long,
    val genreIds: Set<Long>,
)
