package io.goblin.hw11.service

import io.goblin.hw11.dto.BookDto
import io.goblin.hw11.service.command.CreateBookCommand
import io.goblin.hw11.service.command.UpdateBookCommand

interface BookService {
    suspend fun findById(id: Long): BookDto

    suspend fun findAll(): List<BookDto>

    suspend fun create(command: CreateBookCommand): BookDto

    suspend fun update(command: UpdateBookCommand): BookDto

    suspend fun deleteById(id: Long)
}
