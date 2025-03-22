package io.goblin.hw06.mapper

import io.goblin.hw06.dto.AuthorDto
import io.goblin.hw06.model.Author

fun Author.toDto(): AuthorDto =
    AuthorDto(
        id = requireNotNull(id) { "Author.id must not be null when mapping to DTO" },
        fullName = fullName,
    )
