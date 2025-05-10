package io.goblin.hw11.mapper

import io.goblin.hw11.dto.AuthorDto
import io.goblin.hw11.dto.BookDto
import io.goblin.hw11.dto.GenreDto
import io.goblin.hw11.model.Author
import io.goblin.hw11.model.Book
import io.goblin.hw11.model.BookWithDetails
import io.goblin.hw11.model.Genre

fun Book.toDto(
    author: Author,
    genres: List<Genre>,
): BookDto =
    BookDto(
        id = requireNotNull(id) { "Book.id must not be null when mapping to DTO" },
        title = title,
        author = author.toDto(),
        genres = genres.map { it.toDto() },
    )

fun BookWithDetails.toDto(): BookDto =
    BookDto(
        id = id,
        title = title,
        author = AuthorDto(authorId, authorFullName),
        genres = genres.map { (genreId, genreName) -> GenreDto(id = genreId, name = genreName) },
    )
