package io.goblin.hw11.service

import io.goblin.hw11.dto.GenreDto

interface GenreService {
    suspend fun findAll(): List<GenreDto>
}
