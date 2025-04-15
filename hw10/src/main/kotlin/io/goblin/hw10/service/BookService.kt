package io.goblin.hw10.service

import io.goblin.hw10.dto.BookDto
import io.goblin.hw10.dto.CreateBookRequest
import io.goblin.hw10.dto.UpdateBookRequest

interface BookService {
    fun findById(id: Long): BookDto

    fun findAll(): List<BookDto>

    fun create(dto: CreateBookRequest): BookDto

    fun update(
        id: Long,
        dto: UpdateBookRequest,
    ): BookDto

    fun deleteById(id: Long)
}
