package io.goblin.hw11.persistence.repository.impl

import io.goblin.hw11.model.Book
import io.goblin.hw11.model.BookWithDetails
import io.goblin.hw11.persistence.repository.BookCustomRepository
import io.goblin.hw11.persistence.request.InsertBookDbRequest
import io.goblin.hw11.persistence.request.UpdateBookDbRequest
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.coroutines.flow.Flow
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@Repository
class BookCustomRepositoryImpl(
    private val client: DatabaseClient,
    private val txOperator: TransactionalOperator,
) : BookCustomRepository {
    override suspend fun insertBookWithGenres(request: InsertBookDbRequest): Book {
        val(title, authorId, genreIds) = request
        return txOperator.executeAndAwait {
            val bookId =
                client
                    .sql(
                        """
                    INSERT INTO books (title, author_id)
                    VALUES (:title, :authorId)
                    RETURNING id
                    """,
                    ).bind("title", title)
                    .bind("authorId", authorId)
                    .map { row -> row.get("id", java.lang.Long::class.java)!!.toLong() }
                    .awaitSingle()

            if (genreIds.isNotEmpty()) {
                val bookIds = List(genreIds.size) { bookId }
                client
                    .sql(
                        """
                    INSERT INTO books_genres (book_id, genre_id)
                    SELECT book_id, genre_id
                    FROM UNNEST(:bookIds::bigint[], :genreIds::bigint[]) AS t(book_id, genre_id)
                    """,
                    ).bind("bookIds", bookIds.toTypedArray())
                    .bind("genreIds", genreIds.toTypedArray())
                    .await()
            }

            Book(id = bookId, title = title, authorId = authorId)
        }
    }

    override suspend fun updateBookWithGenres(request: UpdateBookDbRequest) {
        val (id, title, authorId, genreIds) = request
        return txOperator.executeAndAwait {
            client
                .sql(
                    """
            UPDATE books
            SET title = :title, author_id = :authorId
            WHERE id = :id
            """,
                ).bind("id", id)
                .bind("title", title)
                .bind("authorId", authorId)
                .await()

            client
                .sql(
                    """
            DELETE FROM books_genres WHERE book_id = :id
            """,
                ).bind("id", id)
                .await()

            if (genreIds.isNotEmpty()) {
                val bookIds = List(genreIds.size) { id }
                client
                    .sql(
                        """
                INSERT INTO books_genres (book_id, genre_id)
                SELECT book_id, genre_id
                FROM UNNEST(:bookIds::bigint[], :genreIds::bigint[]) AS t(book_id, genre_id)
                """,
                    ).bind("bookIds", bookIds.toTypedArray())
                    .bind("genreIds", genreIds.toTypedArray())
                    .await()
            }
        }
    }

    override suspend fun findDetailedById(id: Long): BookWithDetails? =
        client
            .sql(
                """
            SELECT 
                b.id, 
                b.title, 
                b.author_id, 
                a.full_name,
                array_agg(g.id) AS genre_ids,
                array_agg(g.name) AS genre_names
            FROM books AS b
            JOIN authors AS a ON b.author_id = a.id
            LEFT JOIN books_genres AS bg ON bg.book_id = b.id
            LEFT JOIN genres AS g ON g.id = bg.genre_id
            WHERE b.id = :id
            GROUP BY b.id, a.full_name
            """,
            ).bind("id", id)
            .map(::mapRowToBookWithDetails)
            .awaitOneOrNull()

    override fun findAllDetailed(): Flow<BookWithDetails> =
        client
            .sql(
                """
            SELECT 
                b.id, 
                b.title, 
                b.author_id, 
                a.full_name,
                array_agg(g.id) AS genre_ids,
                array_agg(g.name) AS genre_names
            FROM books AS b
            JOIN authors AS a ON b.author_id = a.id
            LEFT JOIN books_genres AS bg ON bg.book_id = b.id
            LEFT JOIN genres AS g ON g.id = bg.genre_id
            GROUP BY b.id, a.full_name
            """,
            ).map(::mapRowToBookWithDetails)
            .flow()

    private fun mapRowToBookWithDetails(
        row: Row,
        metadata: RowMetadata,
    ): BookWithDetails {
        val genreIds = row.get("genre_ids", Array<Long>::class.java)?.toList() ?: emptyList()
        val genreNames = row.get("genre_names", Array<String>::class.java)?.toList() ?: emptyList()
        val genres = genreIds.zip(genreNames)

        return BookWithDetails(
            id = row.get("id", java.lang.Long::class.java)!!.toLong(),
            title = row.get("title", String::class.java)!!,
            authorId = row.get("author_id", java.lang.Long::class.java)!!.toLong(),
            authorFullName = row.get("full_name", String::class.java)!!,
            genres = genres,
        )
    }
}
