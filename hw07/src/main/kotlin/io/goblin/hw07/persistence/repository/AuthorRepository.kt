package io.goblin.hw07.persistence.repository

import io.goblin.hw07.model.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long>
