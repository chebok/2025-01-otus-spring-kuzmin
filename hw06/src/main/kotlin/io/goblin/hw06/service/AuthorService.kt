package io.goblin.hw06.service

import io.goblin.hw06.model.Author

interface AuthorService {
    fun findAll(): List<Author>
}
