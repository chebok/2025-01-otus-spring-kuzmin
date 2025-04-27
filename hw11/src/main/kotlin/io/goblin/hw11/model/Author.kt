package io.goblin.hw11.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("authors")
data class Author(
    @Id
    val id: Long? = null,
    @Column("full_name")
    val fullName: String,
)
