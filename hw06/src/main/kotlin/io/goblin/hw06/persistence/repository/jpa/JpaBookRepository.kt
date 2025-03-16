package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.mapper.toDomain
import io.goblin.hw06.mapper.toJpaEntity
import io.goblin.hw06.model.Book
import io.goblin.hw06.persistence.entity.BookEntity
import io.goblin.hw06.persistence.repository.BookRepository
import jakarta.persistence.EntityManager
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType
import org.springframework.stereotype.Repository

@Repository
class JpaBookRepository(
    private val entityManager: EntityManager,
) : BookRepository {
    override fun findAll(): List<Book> {
        val entityGraph = entityManager.getEntityGraph("book-author-entity-graph")
        val query = entityManager.createQuery("select b from BookEntity b", BookEntity::class.java)
        query.setHint(EntityGraphType.FETCH.key, entityGraph)
        return query.resultList.map { it.toDomain() }
    }

    override fun findById(id: Long): Book? {
        val entityGraph = entityManager.getEntityGraph("book-author-entity-graph")
        val properties = mapOf<String, Any>(EntityGraphType.FETCH.key to entityGraph)
        return entityManager.find(BookEntity::class.java, id, properties)?.toDomain()
    }

    override fun save(book: Book): Book {
        val entity = book.toJpaEntity()

        val savedEntity =
            if (entity.id == null) {
                entityManager.persist(entity)
                entityManager.flush()
                entity
            } else {
                entityManager.merge(entity)
            }

        return savedEntity.toDomain()
    }

    override fun deleteById(id: Long) {
        entityManager.find(BookEntity::class.java, id)?.let {
            entityManager.remove(it)
        }
    }
}
