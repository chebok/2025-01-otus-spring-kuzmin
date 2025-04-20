package io.goblin.hw10.persistence.repository

import io.goblin.hw10.model.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long>
