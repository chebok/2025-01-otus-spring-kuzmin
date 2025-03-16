package io.goblin.hw06.service.impl

import io.goblin.hw06.model.Genre
import io.goblin.hw06.persistence.repository.GenreRepository
import io.goblin.hw06.service.GenreService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GenreServiceImpl(
    private val genreRepository: GenreRepository,
) : GenreService {
    @Transactional(readOnly = true)
    override fun findAll(): List<Genre> = genreRepository.findAll()
}
