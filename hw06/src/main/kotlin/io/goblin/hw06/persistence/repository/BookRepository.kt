package io.goblin.hw06.persistence.repository

import io.goblin.hw06.model.Book

interface BookRepository {
    fun findAll(): List<Book>

    fun findById(id: Long): Book?

    fun save(book: Book): Book

    fun deleteById(id: Long)
}
