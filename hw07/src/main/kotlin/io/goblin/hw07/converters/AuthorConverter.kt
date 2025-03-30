package io.goblin.hw07.converters

import io.goblin.hw07.dto.AuthorDto
import org.springframework.stereotype.Component

@Component
class AuthorConverter {
    fun authorToString(author: AuthorDto): String = "Id: ${author.id}, Full name: ${author.fullName}"
}
