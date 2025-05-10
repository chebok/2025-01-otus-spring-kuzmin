package io.goblin.hw11.model

data class BookWithDetails(
    val id: Long,
    val title: String,
    val authorId: Long,
    val authorFullName: String,
    val genres: List<Pair<Long, String>>,
)
