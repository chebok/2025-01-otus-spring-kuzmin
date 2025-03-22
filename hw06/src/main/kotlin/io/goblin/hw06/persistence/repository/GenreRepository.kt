package io.goblin.hw06.persistence.repository

import io.goblin.hw06.model.Genre

interface GenreRepository {
    fun findAll(): List<Genre>

    fun findAllByIds(ids: Set<Long>): List<Genre>
}
