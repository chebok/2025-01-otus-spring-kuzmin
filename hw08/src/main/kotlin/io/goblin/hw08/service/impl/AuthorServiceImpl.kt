package io.goblin.hw08.service.impl

import io.goblin.hw08.dto.AuthorDto
import io.goblin.hw08.mapper.toDto
import io.goblin.hw08.persistence.repository.AuthorRepository
import io.goblin.hw08.service.AuthorService
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository,
) : AuthorService {
    override fun findAll(): List<AuthorDto> = authorRepository.findAll().map { it.toDto() }
}
