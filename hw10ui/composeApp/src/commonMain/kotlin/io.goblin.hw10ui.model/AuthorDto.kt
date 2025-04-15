package io.goblin.hw10ui.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthorDto(
    val id: Int,
    val fullName: String,
)
