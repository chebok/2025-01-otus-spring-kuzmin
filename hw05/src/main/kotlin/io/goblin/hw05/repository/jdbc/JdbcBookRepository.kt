package io.goblin.hw05.repository.jdbc

import io.goblin.hw05.exceptions.EntityNotFoundException
import io.goblin.hw05.model.Author
import io.goblin.hw05.model.Book
import io.goblin.hw05.model.Genre
import io.goblin.hw05.repository.BookRepository
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class JdbcBookRepository(
    private val jdbc: NamedParameterJdbcOperations,
) : BookRepository {
    override fun findAll(): List<Book> =
        jdbc
            .query(
                """
                SELECT b.id, b.title, b.author_id, a.full_name
                FROM books AS b
                LEFT JOIN authors AS a ON b.author_id = a.id
                """.trimIndent(),
                bookRowMapper,
            ).takeIf { it.isNotEmpty() }
            ?.let { books ->
                val genresByBookId = getGenresByBookIds(books.map { it.id }.toSet())
                books.map { it.copy(genres = genresByBookId[it.id].orEmpty().toMutableList()) }
            }
            ?: emptyList()

    override fun findById(id: Long): Book? =
        jdbc
            .query(
                """
                SELECT b.id, b.title, b.author_id, a.full_name
                FROM books as b
                LEFT JOIN authors AS a ON b.author_id = a.id
                WHERE b.id = :id
                """.trimIndent(),
                mapOf("id" to id),
                bookRowMapper,
            ).firstOrNull()
            ?.let { book ->
                val genres = getGenresByBookIds(setOf(book.id))[book.id].orEmpty().toMutableList()
                book.copy(genres = genres)
            }

    override fun save(book: Book): Book =
        if (book.id == 0L) {
            insert(book)
        } else {
            update(book)
        }

    private fun update(book: Book): Book {
        val affectedRows =
            jdbc.update(
                "UPDATE books SET title = :title, author_id = :author_id WHERE id = :id",
                mapOf(
                    "title" to book.title,
                    "author_id" to book.author.id,
                    "id" to book.id,
                ),
            )

        if (affectedRows == 0) {
            throw EntityNotFoundException("Book with id ${book.id} not found")
        }

        removeGenresRelationsFor(book)
        batchInsertGenresRelationsFor(book)

        return book
    }

    private fun insert(book: Book): Book {
        val keyHolder = GeneratedKeyHolder()
        val params =
            MapSqlParameterSource()
                .addValue("title", book.title)
                .addValue("author_id", book.author.id)

        jdbc.update(
            "INSERT INTO books (title, author_id) VALUES (:title, :author_id)",
            params,
            keyHolder,
            arrayOf("id"),
        )

        val generatedId =
            keyHolder.key?.toLong()
                ?: throw IllegalStateException("Internal server error: failed to retrieve generated ID for book '${book.title}'.")

        val bookWithId = book.copy(id = generatedId)

        batchInsertGenresRelationsFor(bookWithId)

        return bookWithId
    }

    private fun removeGenresRelationsFor(book: Book) {
        jdbc.update(
            """
            DELETE FROM books_genres WHERE book_id = :id
            """.trimIndent(),
            mapOf("id" to book.id),
        )
    }

    private fun batchInsertGenresRelationsFor(book: Book) {
        val sql = "INSERT INTO books_genres (book_id, genre_id) VALUES (:bookId, :genreId)"

        val batchParams =
            book.genres.map { genre ->
                mapOf("bookId" to book.id, "genreId" to genre.id)
            }

        jdbc.batchUpdate(sql, batchParams.toTypedArray())
    }

    override fun deleteById(id: Long) {
        jdbc.update(
            """
            DELETE FROM books AS b
            WHERE b.id = :id
            """.trimIndent(),
            mapOf("id" to id),
        )
    }

    private fun getGenresByBookIds(bookIds: Set<Long>): Map<Long, MutableList<Genre>> =
        jdbc.query(
            """
            SELECT 
                bg.book_id, 
                g.id AS genre_id, 
                g.name AS genre_name
            FROM books_genres AS bg
            JOIN genres AS g ON bg.genre_id = g.id
            WHERE bg.book_id IN (:bookIds);
            """,
            mapOf("bookIds" to bookIds.toList()),
            bookGenresExtractor,
        ) ?: emptyMap()

    companion object {
        private val bookRowMapper =
            RowMapper { rs: ResultSet, _: Int ->
                Book(
                    id = rs.getLong("id"),
                    title = rs.getString("title"),
                    author =
                        Author(
                            id = rs.getLong("author_id"),
                            fullName = rs.getString("full_name"),
                        ),
                )
            }

        private val bookGenresExtractor =
            ResultSetExtractor { rs: ResultSet ->
                mutableMapOf<Long, MutableList<Genre>>()
                    .apply {
                        while (rs.next()) {
                            val bookId = rs.getLong("book_id")
                            val genre =
                                Genre(
                                    id = rs.getLong("genre_id"),
                                    name = rs.getString("genre_name"),
                                )
                            computeIfAbsent(bookId) { mutableListOf() }.add(genre)
                        }
                    }.toMap()
            }
    }
}
