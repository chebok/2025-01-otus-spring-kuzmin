package io.goblin.hw07.mapper

import io.goblin.hw07.dto.AuthorDto
import io.goblin.hw07.model.Author

fun Author.toDto(): AuthorDto =
    AuthorDto(
        id = requireNotNull(id) { "Author.id must not be null when mapping to DTO" },
        fullName = fullName,
    )
