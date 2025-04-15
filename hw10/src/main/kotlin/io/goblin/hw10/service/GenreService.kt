package io.goblin.hw10.service

import io.goblin.hw10.dto.GenreDto

interface GenreService {
    fun findAll(): List<GenreDto>
}
