package io.goblin.hw07.service

import io.goblin.hw07.dto.BookDto

interface BookService {
    fun findById(id: Long): BookDto?

    fun findAll(): List<BookDto>

    fun insert(
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): BookDto

    fun update(
        id: Long,
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): BookDto

    fun deleteById(id: Long)
}
