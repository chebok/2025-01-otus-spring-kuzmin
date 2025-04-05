package io.goblin.hw08.converters

import io.goblin.hw08.dto.GenreDto
import org.springframework.stereotype.Component

@Component
class GenreConverter {
    fun genreToString(genre: GenreDto): String = "Id: ${genre.id}, Name: ${genre.name}"
}
