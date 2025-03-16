package io.goblin.hw06.model

data class Book(
    val id: Long,
    val title: String,
    val author: Author,
    val genres: List<Genre> = listOf(),
)
