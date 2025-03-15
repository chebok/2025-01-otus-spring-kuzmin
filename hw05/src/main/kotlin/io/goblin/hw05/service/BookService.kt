package io.goblin.hw05.service

import io.goblin.hw05.model.Book

interface BookService {
    fun findById(id: Long): Book?

    fun findAll(): List<Book>

    fun insert(
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): Book

    fun update(
        id: Long,
        title: String,
        authorId: Long,
        genresIds: Set<Long>,
    ): Book

    fun deleteById(id: Long)
}
