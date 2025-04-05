package io.goblin.hw08.persistence.repository

import io.goblin.hw08.model.Author
import org.springframework.data.mongodb.repository.MongoRepository

interface AuthorRepository : MongoRepository<Author, String>
