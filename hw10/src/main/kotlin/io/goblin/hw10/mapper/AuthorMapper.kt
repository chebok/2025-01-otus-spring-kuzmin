package io.goblin.hw10.mapper

import io.goblin.hw10.dto.AuthorDto
import io.goblin.hw10.model.Author

fun Author.toDto(): AuthorDto =
    AuthorDto(
        id = requireNotNull(id) { "Author.id must not be null when mapping to DTO" },
        fullName = fullName,
    )
