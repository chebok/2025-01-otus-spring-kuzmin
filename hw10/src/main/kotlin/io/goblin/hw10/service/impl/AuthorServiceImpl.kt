package io.goblin.hw10.service.impl

import io.goblin.hw10.dto.AuthorDto
import io.goblin.hw10.mapper.toDto
import io.goblin.hw10.persistence.repository.AuthorRepository
import io.goblin.hw10.service.AuthorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository,
) : AuthorService {
    @Transactional(readOnly = true)
    override fun findAll(): List<AuthorDto> = authorRepository.findAll().map { it.toDto() }
}
