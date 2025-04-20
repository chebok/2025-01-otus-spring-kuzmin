package io.goblin.hw10.service

import io.goblin.hw10.dto.BookDto
import io.goblin.hw10.service.command.CreateBookCommand
import io.goblin.hw10.service.command.UpdateBookCommand

interface BookService {
    fun findById(id: Long): BookDto

    fun findAll(): List<BookDto>

    fun create(command: CreateBookCommand): BookDto

    fun update(command: UpdateBookCommand): BookDto

    fun deleteById(id: Long)
}
