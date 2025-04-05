package io.goblin.hw07.service

import io.goblin.hw07.dto.AuthorDto

interface AuthorService {
    fun findAll(): List<AuthorDto>
}
