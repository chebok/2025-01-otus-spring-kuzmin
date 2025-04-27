package io.goblin.hw11.persistence.repository

import io.goblin.hw11.model.Genre
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface GenreRepository : CoroutineCrudRepository<Genre, Long>
