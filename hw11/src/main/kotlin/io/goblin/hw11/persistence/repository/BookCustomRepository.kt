package io.goblin.hw11.persistence.repository

import io.goblin.hw11.model.Book
import io.goblin.hw11.model.BookWithDetails
import io.goblin.hw11.persistence.request.InsertBookDbRequest
import io.goblin.hw11.persistence.request.UpdateBookDbRequest
import kotlinx.coroutines.flow.Flow

interface BookCustomRepository {
    suspend fun insertBookWithGenres(request: InsertBookDbRequest): Book

    suspend fun updateBookWithGenres(request: UpdateBookDbRequest)

    suspend fun findDetailedById(id: Long): BookWithDetails?

    fun findAllDetailed(): Flow<BookWithDetails>
}
