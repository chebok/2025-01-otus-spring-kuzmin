package io.goblin.hw07.model

import jakarta.persistence.*

@Entity
@Table(name = "authors")
class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "full_name", nullable = false)
    val fullName: String,
)
