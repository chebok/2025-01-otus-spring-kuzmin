package io.goblin.hw11.persistence.repository

import io.goblin.hw11.model.Author
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AuthorRepository : CoroutineCrudRepository<Author, Long>
