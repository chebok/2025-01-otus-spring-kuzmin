package io.goblin.hw11.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("book_comments")
data class BookComment(
    @Id
    val id: Long? = null,
    @Column("text")
    val text: String,
    @Column("book_id")
    val bookId: Long,
)
