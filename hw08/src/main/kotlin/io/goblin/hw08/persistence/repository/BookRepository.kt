package io.goblin.hw08.persistence.repository

import io.goblin.hw08.model.Book
import org.springframework.data.mongodb.repository.MongoRepository

interface BookRepository : MongoRepository<Book, String>
