package io.goblin.hw08.dto

data class BookDto(
    val id: String,
    val title: String,
    val author: AuthorDto,
    val genres: List<GenreDto> = listOf(),
)
