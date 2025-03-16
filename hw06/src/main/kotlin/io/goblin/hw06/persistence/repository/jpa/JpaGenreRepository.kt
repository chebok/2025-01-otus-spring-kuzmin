package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.mapper.toDomain
import io.goblin.hw06.model.Genre
import io.goblin.hw06.persistence.entity.GenreEntity
import io.goblin.hw06.persistence.repository.GenreRepository
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class JpaGenreRepository(
    private val entityManager: EntityManager,
) : GenreRepository {
    override fun findAll(): List<Genre> =
        entityManager.createQuery("select g from GenreEntity g", GenreEntity::class.java).resultList.map {
            it.toDomain()
        }

    override fun findAllByIds(ids: Set<Long>): List<Genre> {
        val query = entityManager.createQuery("select g from GenreEntity g where g.id in :ids", GenreEntity::class.java)
        query.setParameter("ids", ids)
        return query.resultList.map { it.toDomain() }
    }
}
