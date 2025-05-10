package io.goblin.hw11.service

import io.goblin.hw11.dto.AuthorDto

interface AuthorService {
    suspend fun findAll(): List<AuthorDto>
}
