package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.model.BookComment
import io.goblin.hw06.persistence.repository.BookCommentRepository
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class JpaBookCommentRepository(
    private val entityManager: EntityManager,
) : BookCommentRepository {
    override fun findById(id: Long): BookComment? = entityManager.find(BookComment::class.java, id)

    override fun findByBookId(bookId: Long): List<BookComment> {
        val query =
            entityManager.createQuery(
                "select bc from BookComment bc where bc.bookId = :bookId",
                BookComment::class.java,
            )
        query.setParameter("bookId", bookId)
        return query.resultList
    }

    override fun save(bookComment: BookComment): BookComment {
        val savedEntity =
            if (bookComment.id == null) {
                entityManager.persist(bookComment)
                bookComment
            } else {
                entityManager.merge(bookComment)
            }
        return savedEntity
    }

    override fun deleteById(id: Long) {
        entityManager.find(BookComment::class.java, id)?.let {
            entityManager.remove(it)
        }
    }
}
