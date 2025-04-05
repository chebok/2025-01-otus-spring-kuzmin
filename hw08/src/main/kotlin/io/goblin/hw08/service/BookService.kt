package io.goblin.hw08.service

import io.goblin.hw08.dto.BookDto

interface BookService {
    fun findById(id: String): BookDto?

    fun findAll(): List<BookDto>

    fun insert(
        title: String,
        authorId: String,
        genresIds: Set<String>,
    ): BookDto

    fun update(
        id: String,
        title: String,
        authorId: String,
        genresIds: Set<String>,
    ): BookDto

    fun deleteById(id: String)
}
