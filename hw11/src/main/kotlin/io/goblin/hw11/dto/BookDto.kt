package io.goblin.hw11.dto

data class BookDto(
    val id: Long,
    val title: String,
    val author: AuthorDto,
    val genres: List<GenreDto> = listOf(),
)
