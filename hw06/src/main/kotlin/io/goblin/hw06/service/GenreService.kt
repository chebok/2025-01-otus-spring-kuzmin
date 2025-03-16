package io.goblin.hw06.service

import io.goblin.hw06.model.Genre

interface GenreService {
    fun findAll(): List<Genre>
}
