package io.goblin.hw11.mapper

import io.goblin.hw11.dto.AuthorDto
import io.goblin.hw11.model.Author

fun Author.toDto(): AuthorDto =
    AuthorDto(
        id = requireNotNull(id) { "Author.id must not be null when mapping to DTO" },
        fullName = fullName,
    )
