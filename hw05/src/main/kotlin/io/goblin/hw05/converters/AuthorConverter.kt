package io.goblin.hw05.converters

import io.goblin.hw05.model.Author
import org.springframework.stereotype.Component

@Component
class AuthorConverter {
    fun authorToString(author: Author): String = "Id: ${author.id}, Full name: ${author.fullName}"
}
