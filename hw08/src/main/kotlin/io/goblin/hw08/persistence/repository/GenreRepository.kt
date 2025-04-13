package io.goblin.hw08.persistence.repository

import io.goblin.hw08.model.Genre
import org.springframework.data.mongodb.repository.MongoRepository

interface GenreRepository : MongoRepository<Genre, String>
