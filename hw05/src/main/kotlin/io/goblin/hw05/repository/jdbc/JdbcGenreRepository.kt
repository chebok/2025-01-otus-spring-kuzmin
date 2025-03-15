package io.goblin.hw05.repository.jdbc

import io.goblin.hw05.model.Genre
import io.goblin.hw05.repository.GenreRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class JdbcGenreRepository(
    private val jdbc: NamedParameterJdbcOperations,
) : GenreRepository {
    override fun findAll(): List<Genre> = jdbc.query("SELECT id, name FROM genres", genreRowMapper)

    override fun findAllByIds(ids: Set<Long>): List<Genre> =
        jdbc.query("SELECT id, name FROM genres WHERE id IN (:ids)", mapOf("ids" to ids.toList()), genreRowMapper)

    companion object {
        private val genreRowMapper =
            RowMapper { rs: ResultSet, _: Int ->
                Genre(rs.getLong("id"), rs.getString("name"))
            }
    }
}
