package io.goblin.hw10ui.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateBookRequest(
    val title: String,
    val authorId: Int,
    val genreIds: List<Int>,
)
