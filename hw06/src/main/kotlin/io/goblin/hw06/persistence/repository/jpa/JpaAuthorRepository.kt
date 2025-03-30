package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.model.Author
import io.goblin.hw06.persistence.repository.AuthorRepository
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class JpaAuthorRepository(
    private val entityManager: EntityManager,
) : AuthorRepository {
    override fun findAll(): List<Author> = entityManager.createQuery("select a from Author a", Author::class.java).resultList

    override fun findById(id: Long): Author? = entityManager.find(Author::class.java, id)
}
