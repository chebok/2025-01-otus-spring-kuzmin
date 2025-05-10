package io.goblin.hw11.persistence.repository

interface BookGenreRepository {
    suspend fun deleteAllByBookId(bookId: Long)

    suspend fun insertAll(
        bookId: Long,
        genreIds: Set<Long>,
    )
}
