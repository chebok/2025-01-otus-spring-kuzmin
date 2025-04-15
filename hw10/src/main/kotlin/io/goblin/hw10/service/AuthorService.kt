package io.goblin.hw10.service

import io.goblin.hw10.dto.AuthorDto

interface AuthorService {
    fun findAll(): List<AuthorDto>
}
