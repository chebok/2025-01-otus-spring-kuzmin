package io.goblin.hw06.service

import io.goblin.hw06.dto.GenreDto

interface GenreService {
    fun findAll(): List<GenreDto>
}
