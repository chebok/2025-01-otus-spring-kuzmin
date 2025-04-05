package io.goblin.hw08.mapper

import io.goblin.hw08.dto.AuthorDto
import io.goblin.hw08.model.Author

fun Author.toDto(): AuthorDto =
    AuthorDto(
        id = requireNotNull(id) { "Author.id must not be null when mapping to DTO" },
        fullName = fullName,
    )
