package io.goblin.hw11.persistence.repository

import io.goblin.hw11.model.BookWithDetails
import kotlinx.coroutines.flow.Flow

interface BookCustomRepository {
    suspend fun findDetailedById(id: Long): BookWithDetails?

    fun findAllDetailed(): Flow<BookWithDetails>
}
