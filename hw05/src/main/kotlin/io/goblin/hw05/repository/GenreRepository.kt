package io.goblin.hw05.repository

import io.goblin.hw05.model.Genre

interface GenreRepository {
    fun findAll(): List<Genre>

    fun findAllByIds(ids: Set<Long>): List<Genre>
}
