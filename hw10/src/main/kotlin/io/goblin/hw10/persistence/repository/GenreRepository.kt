package io.goblin.hw10.persistence.repository

import io.goblin.hw10.model.Genre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GenreRepository : JpaRepository<Genre, Long> {
    @Query("select g from Genre g where g.id in :ids")
    fun findAllByIds(
        @Param("ids") ids: Set<Long>,
    ): List<Genre>
}
