package io.goblin.hw05.repository.jdbc

import io.goblin.hw05.model.Author
import io.goblin.hw05.repository.AuthorRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class JdbcAuthorRepository(
    private val jdbc: NamedParameterJdbcOperations,
) : AuthorRepository {
    override fun findAll(): List<Author> = jdbc.query("SELECT id, full_name FROM authors", authorRowMapper)

    override fun findById(id: Long): Author? =
        jdbc
            .query("SELECT id, full_name FROM authors WHERE id = :id", mapOf("id" to id), authorRowMapper)
            .firstOrNull()

    companion object {
        private val authorRowMapper =
            RowMapper { rs: ResultSet, _: Int ->
                Author(rs.getLong("id"), rs.getString("full_name"))
            }
    }
}
