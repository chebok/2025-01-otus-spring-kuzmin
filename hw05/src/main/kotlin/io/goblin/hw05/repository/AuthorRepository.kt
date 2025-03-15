package io.goblin.hw05.repository

import io.goblin.hw05.model.Author

interface AuthorRepository {
    fun findAll(): List<Author>

    fun findById(id: Long): Author?
}
