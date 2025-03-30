package io.goblin.hw07.model

import jakarta.persistence.*

@Entity
@Table(name = "book_comments")
class BookComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "text", nullable = false)
    val text: String,
    @Column(name = "book_id", nullable = false)
    val bookId: Long,
)
