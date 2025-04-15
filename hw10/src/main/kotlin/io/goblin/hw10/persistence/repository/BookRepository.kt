package io.goblin.hw10.persistence.repository

import io.goblin.hw10.model.Book
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRepository : JpaRepository<Book, Long> {
    @EntityGraph(value = "book-author-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    override fun findAll(): List<Book>

    @EntityGraph(value = "book-author-genres-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    override fun findById(id: Long): Optional<Book>
}
