package io.goblin.hw06.converters

import io.goblin.hw06.dto.AuthorDto
import org.springframework.stereotype.Component

@Component
class AuthorConverter {
    fun authorToString(author: AuthorDto): String = "Id: ${author.id}, Full name: ${author.fullName}"
}
