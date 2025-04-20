package io.goblin.hw08.converters

import io.goblin.hw08.dto.BookDto
import org.springframework.stereotype.Component

@Component
class BookConverter(
    private val authorConverter: AuthorConverter,
    private val genreConverter: GenreConverter,
) {
    fun bookToString(book: BookDto): String {
        val authorString = authorConverter.authorToString(book.author)
        val genresString =
            book.genres
                .map { genreConverter.genreToString(it) }
                .joinToString(", ") { "{$it}" }
        return "Id: ${book.id}, Title: ${book.title}, Author: {$authorString}, Genres: [$genresString]"
    }
}
