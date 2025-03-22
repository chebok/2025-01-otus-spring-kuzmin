package io.goblin.hw06.converters

import io.goblin.hw06.dto.GenreDto
import org.springframework.stereotype.Component

@Component
class GenreConverter {
    fun genreToString(genre: GenreDto): String = "Id: ${genre.id}, Name: ${genre.name}"
}
