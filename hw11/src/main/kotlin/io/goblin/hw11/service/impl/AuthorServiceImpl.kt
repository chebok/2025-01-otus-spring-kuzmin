package io.goblin.hw11.service.impl

import io.goblin.hw11.dto.AuthorDto
import io.goblin.hw11.mapper.toDto
import io.goblin.hw11.persistence.repository.AuthorRepository
import io.goblin.hw11.service.AuthorService
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository,
) : AuthorService {
    override suspend fun findAll(): List<AuthorDto> = authorRepository.findAll().toList().map { it.toDto() }
}
