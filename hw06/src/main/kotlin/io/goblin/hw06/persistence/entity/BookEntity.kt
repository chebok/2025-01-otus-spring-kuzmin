package io.goblin.hw06.persistence.entity

import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "books")
@NamedEntityGraph(
    name = "book-author-entity-graph",
    attributeNodes = [
        NamedAttributeNode("author"),
    ],
)
class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "title", nullable = false)
    val title: String,
    @ManyToOne(targetEntity = AuthorEntity::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    val author: AuthorEntity,
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = GenreEntity::class, fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "books_genres",
        joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "genre_id", referencedColumnName = "id")],
    )
    val genres: List<GenreEntity> = emptyList(),
)
