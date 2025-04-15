package io.goblin.hw10ui.model

import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val id: Int,
    val title: String,
    val author: AuthorDto,
    val genres: List<GenreDto>,
)
