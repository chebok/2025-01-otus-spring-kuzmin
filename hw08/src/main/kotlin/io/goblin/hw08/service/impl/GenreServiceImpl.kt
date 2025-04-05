package io.goblin.hw08.service.impl

import io.goblin.hw08.dto.GenreDto
import io.goblin.hw08.mapper.toDto
import io.goblin.hw08.persistence.repository.GenreRepository
import io.goblin.hw08.service.GenreService
import org.springframework.stereotype.Service

@Service
class GenreServiceImpl(
    private val genreRepository: GenreRepository,
) : GenreService {
    override fun findAll(): List<GenreDto> = genreRepository.findAll().map { it.toDto() }
}
