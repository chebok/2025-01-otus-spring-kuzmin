package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.mapper.toDomain
import io.goblin.hw06.mapper.toJpaEntity
import io.goblin.hw06.model.BookComment
import io.goblin.hw06.persistence.entity.BookCommentEntity
import io.goblin.hw06.persistence.repository.BookCommentRepository
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class JpaBookCommentRepository(
    private val entityManager: EntityManager,
) : BookCommentRepository {
    override fun findById(id: Long): BookComment? = entityManager.find(BookCommentEntity::class.java, id)?.toDomain()

    override fun findByBookId(bookId: Long): List<BookComment> {
        val query =
            entityManager.createQuery(
                "select bc from BookCommentEntity bc where bc.bookId = :bookId",
                BookCommentEntity::class.java,
            )
        query.setParameter("bookId", bookId)
        return query.resultList.map { it.toDomain() }
    }

    override fun save(bookComment: BookComment): BookComment {
        val entity = bookComment.toJpaEntity()

        val savedEntity =
            if (entity.id == null) {
                entityManager.persist(entity)
                entity
            } else {
                entityManager.merge(entity)
            }

        return savedEntity.toDomain()
    }

    override fun deleteById(id: Long) {
        entityManager.find(BookCommentEntity::class.java, id)?.let {
            entityManager.remove(it)
        }
    }
}
