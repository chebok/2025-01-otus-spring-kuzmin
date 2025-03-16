package io.goblin.hw06.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "authors")
class AuthorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "full_name", nullable = false)
    val fullName: String,
)
