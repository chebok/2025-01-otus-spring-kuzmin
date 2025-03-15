package io.goblin.hw05.repository

import io.goblin.hw05.model.Book

interface BookRepository {
    fun findAll(): List<Book>

    fun findById(id: Long): Book?

    fun save(book: Book): Book

    fun deleteById(id: Long)
}
