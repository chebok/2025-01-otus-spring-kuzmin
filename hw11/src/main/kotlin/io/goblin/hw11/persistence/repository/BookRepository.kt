package io.goblin.hw11.persistence.repository

import io.goblin.hw11.model.Book
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BookRepository : CoroutineCrudRepository<Book, Long>
