package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.mapper.toDomain
import io.goblin.hw06.model.Author
import io.goblin.hw06.persistence.entity.AuthorEntity
import io.goblin.hw06.persistence.repository.AuthorRepository
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class JpaAuthorRepository(
    private val entityManager: EntityManager,
) : AuthorRepository {
    override fun findAll(): List<Author> =
        entityManager.createQuery("select a from AuthorEntity a", AuthorEntity::class.java).resultList.map {
            it.toDomain()
        }

    override fun findById(id: Long): Author? = entityManager.find(AuthorEntity::class.java, id)?.toDomain()
}
