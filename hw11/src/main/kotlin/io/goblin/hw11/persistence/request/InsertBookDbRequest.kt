package io.goblin.hw11.persistence.request

data class InsertBookDbRequest(
    val title: String,
    val authorId: Long,
    val genreIds: Set<Long>,
)
