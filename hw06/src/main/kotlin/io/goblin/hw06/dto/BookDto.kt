package io.goblin.hw06.dto

data class BookDto(
    val id: Long,
    val title: String,
    val author: AuthorDto,
    val genres: List<GenreDto> = listOf(),
)
