package io.goblin.hw11.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("books")
data class Book(
    @Id
    val id: Long? = null,
    @Column("title")
    val title: String,
    @Column("author_id")
    val authorId: Long,
)
