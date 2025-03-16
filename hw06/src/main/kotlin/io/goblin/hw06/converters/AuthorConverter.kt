package io.goblin.hw06.converters

import io.goblin.hw06.model.Author
import org.springframework.stereotype.Component

@Component
class AuthorConverter {
    fun authorToString(author: Author): String = "Id: ${author.id}, Full name: ${author.fullName}"
}
