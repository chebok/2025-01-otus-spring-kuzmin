package io.goblin.hw11.service.impl

import io.goblin.hw11.dto.GenreDto
import io.goblin.hw11.mapper.toDto
import io.goblin.hw11.persistence.repository.GenreRepository
import io.goblin.hw11.service.GenreService
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class GenreServiceImpl(
    private val genreRepository: GenreRepository,
) : GenreService {
    override suspend fun findAll(): List<GenreDto> = genreRepository.findAll().toList().map { it.toDto() }
}
