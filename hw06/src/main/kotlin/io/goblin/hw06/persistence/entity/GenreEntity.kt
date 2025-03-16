package io.goblin.hw06.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "genres")
class GenreEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name", nullable = false)
    val name: String,
)
