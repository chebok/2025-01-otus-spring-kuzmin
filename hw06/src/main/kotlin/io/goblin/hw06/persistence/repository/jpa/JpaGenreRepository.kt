package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.model.Genre
import io.goblin.hw06.persistence.repository.GenreRepository
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class JpaGenreRepository(
    private val entityManager: EntityManager,
) : GenreRepository {
    override fun findAll(): List<Genre> = entityManager.createQuery("select g from Genre g", Genre::class.java).resultList

    override fun findAllByIds(ids: Set<Long>): List<Genre> {
        val query = entityManager.createQuery("select g from Genre g where g.id in :ids", Genre::class.java)
        query.setParameter("ids", ids)
        return query.resultList
    }
}
