package io.goblin.hw05.service

import io.goblin.hw05.model.Author

interface AuthorService {
    fun findAll(): List<Author>
}
