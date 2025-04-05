package io.goblin.hw08.service

import io.goblin.hw08.dto.GenreDto

interface GenreService {
    fun findAll(): List<GenreDto>
}
