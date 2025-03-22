package io.goblin.hw06.service

import io.goblin.hw06.dto.AuthorDto

interface AuthorService {
    fun findAll(): List<AuthorDto>
}
