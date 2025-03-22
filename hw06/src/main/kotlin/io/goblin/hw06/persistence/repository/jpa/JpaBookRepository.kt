package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.model.Book
import io.goblin.hw06.persistence.repository.BookRepository
import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType
import org.springframework.stereotype.Repository

@Repository
class JpaBookRepository(
    private val entityManager: EntityManager,
) : BookRepository {
    override fun findAll(): List<Book> {
        val entityGraph = entityManager.getEntityGraph("book-author-genres-entity-graph")
        val query = entityManager.createQuery("select b from Book b", Book::class.java)
        query.setHint(EntityGraphType.FETCH.key, entityGraph)
        return query.resultList
    }

    override fun findById(id: Long): Book? {
        val entityGraph = entityManager.getEntityGraph("book-author-entity-graph")
        val properties = mapOf<String, Any>(EntityGraphType.FETCH.key to entityGraph)
        return entityManager.find(Book::class.java, id, properties)
    }

    override fun save(book: Book): Book {
        val savedEntity =
            if (book.id == null) {
                entityManager.persist(book)
                book
            } else {
                entityManager.merge(book)
            }

        return savedEntity
    }

    override fun deleteById(id: Long) {
        entityManager.find(Book::class.java, id)?.let {
            entityManager.remove(it)
        }
    }
}
