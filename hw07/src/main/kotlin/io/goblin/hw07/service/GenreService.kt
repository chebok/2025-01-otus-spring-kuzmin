package io.goblin.hw07.service

import io.goblin.hw07.dto.GenreDto

interface GenreService {
    fun findAll(): List<GenreDto>
}
