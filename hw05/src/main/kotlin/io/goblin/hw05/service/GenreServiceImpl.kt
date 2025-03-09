package io.goblin.hw05.service

import io.goblin.hw05.model.Genre
import io.goblin.hw05.repository.GenreRepository
import org.springframework.stereotype.Service

@Service
class GenreServiceImpl(
    private val genreRepository: GenreRepository,
) : GenreService {
    override fun findAll(): List<Genre> = genreRepository.findAll()
}
