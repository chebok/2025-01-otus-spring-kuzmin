package io.goblin.hw07.service.impl

import io.goblin.hw07.dto.GenreDto
import io.goblin.hw07.mapper.toDto
import io.goblin.hw07.persistence.repository.GenreRepository
import io.goblin.hw07.service.GenreService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenreServiceImpl(
    private val genreRepository: GenreRepository,
) : GenreService {
    @Transactional(readOnly = true)
    override fun findAll(): List<GenreDto> = genreRepository.findAll().map { it.toDto() }
}
