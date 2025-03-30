package io.goblin.hw06.persistence.repository

import io.goblin.hw06.model.Author

interface AuthorRepository {
    fun findAll(): List<Author>

    fun findById(id: Long): Author?
}
