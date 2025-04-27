package io.goblin.hw11.persistence.repository.impl

import io.goblin.hw11.persistence.repository.BookGenreRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Repository

@Repository
class BookGenreRepositoryImpl(
    private val client: DatabaseClient,
) : BookGenreRepository {
    override suspend fun deleteAllByBookId(bookId: Long) {
        client
            .sql("DELETE FROM books_genres WHERE book_id = :bookId")
            .bind("bookId", bookId)
            .await()
    }

    override suspend fun insertAll(
        bookId: Long,
        genreIds: Set<Long>,
    ) {
        if (genreIds.isEmpty()) return

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
}
