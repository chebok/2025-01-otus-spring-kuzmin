package io.goblin.hw06.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "book_comments")
class BookCommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "text", nullable = false)
    val text: String,
    @Column(name = "book_id", nullable = false)
    val bookId: Long,
)
