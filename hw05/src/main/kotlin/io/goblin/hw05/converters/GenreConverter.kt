package io.goblin.hw05.converters

import io.goblin.hw05.model.Genre
import org.springframework.stereotype.Component

@Component
class GenreConverter {
    fun genreToString(genre: Genre): String = "Id: ${genre.id}, Name: ${genre.name}"
}
