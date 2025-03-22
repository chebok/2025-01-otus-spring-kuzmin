package io.goblin.hw06.model

import jakarta.persistence.*

@Entity
@Table(name = "genres")
class Genre(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name", nullable = false)
    val name: String,
)
