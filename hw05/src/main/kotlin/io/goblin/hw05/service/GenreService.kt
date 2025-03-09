package io.goblin.hw05.service

import io.goblin.hw05.model.Genre

interface GenreService {
    fun findAll(): List<Genre>
}
