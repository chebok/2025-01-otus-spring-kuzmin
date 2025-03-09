package io.goblin.hw05.service

import io.goblin.hw05.model.Author
import io.goblin.hw05.repository.AuthorRepository
import org.springframework.stereotype.Service

@Service
class AuthorServiceImpl(
    private val authorRepository: AuthorRepository,
) : AuthorService {
    override fun findAll(): List<Author> = authorRepository.findAll()
}
