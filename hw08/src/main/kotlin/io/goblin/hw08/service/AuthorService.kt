package io.goblin.hw08.service

import io.goblin.hw08.dto.AuthorDto

interface AuthorService {
    fun findAll(): List<AuthorDto>
}
