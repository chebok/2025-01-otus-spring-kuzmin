package io.goblin.hw08.mongo.changelog

import com.github.cloudyrock.mongock.ChangeLog
import com.github.cloudyrock.mongock.ChangeSet
import com.mongodb.client.MongoDatabase
import io.goblin.hw08.model.Author
import io.goblin.hw08.model.Book
import io.goblin.hw08.model.BookComment
import io.goblin.hw08.model.Genre
import io.goblin.hw08.persistence.repository.AuthorRepository
import io.goblin.hw08.persistence.repository.BookCommentRepository
import io.goblin.hw08.persistence.repository.BookRepository
import io.goblin.hw08.persistence.repository.GenreRepository
import org.bson.Document

@ChangeLog(order = "001")
class V001_InitData {
    private val authors = mutableListOf<Author>()
    private val genres = mutableListOf<Genre>()
    private val books = mutableListOf<Book>()

    @ChangeSet(order = "000", id = "dropDB", author = "goblin", runAlways = true)
    fun dropDB(db: MongoDatabase) {
        db.drop()
    }

    @ChangeSet(order = "005", id = "createIndexOnBookId", author = "goblin", runAlways = true)
    fun createIndexOnBookId(db: MongoDatabase) {
        db
            .getCollection("book_comments")
            .createIndex(Document("bookId", 1))
    }

    @ChangeSet(order = "010", id = "initAuthors", author = "goblin", runAlways = true)
    fun initAuthors(authorRepository: AuthorRepository) {
        authorNames.forEach { name ->
            authorRepository.save(Author(fullName = name)).also {
                authors.add(it)
            }
        }
    }

    @ChangeSet(order = "020", id = "initGenres", author = "goblin", runAlways = true)
    fun initGenres(genreRepository: GenreRepository) {
        genreNames.forEach { name ->
            genreRepository.save(Genre(name = name)).also {
                genres.add(it)
            }
        }
    }

    @ChangeSet(order = "030", id = "initBooks", author = "goblin", runAlways = true)
    fun initBooks(bookRepository: BookRepository) {
        bookRelationList.forEach { (title, authorIdx, genreIndices) ->
            val book =
                Book(
                    title = title,
                    author = authors[authorIdx],
                    genres =
                        genreIndices.map { genreIdx ->
                            Book.Genre(
                                id = requireNotNull(genres[genreIdx].id),
                                name = genres[genreIdx].name,
                            )
                        },
                )
            bookRepository.save(book).also {
                books.add(it)
            }
        }
    }

    @ChangeSet(order = "040", id = "initBookComments", author = "goblin", runAlways = true)
    fun initBookComments(bookCommentRepository: BookCommentRepository) {
        bookCommentMap.forEach { (bookIdx, comments) ->
            comments.forEach { text ->
                bookCommentRepository.save(
                    BookComment(
                        text = text,
                        bookId = requireNotNull(books[bookIdx].id),
                    ),
                )
            }
        }
    }

    companion object {
        val authorNames =
            listOf(
                "H.P. Lovecraft",
                "Isaac Asimov",
                "Agatha Christie",
            )

        val genreNames =
            listOf(
                "Horror",
                "Science Fiction",
                "Detective",
                "Fantasy",
                "Mystery",
                "Cyberpunk",
            )

        val bookRelationList =
            listOf(
                Triple("The Call of Cthulhu", 0, listOf(0, 3)),
                Triple("Foundation", 1, listOf(1, 5)),
                Triple("Murder on the Orient Express", 2, listOf(2, 4)),
            )

        val bookCommentMap =
            mapOf(
                0 to listOf("Terrifying and immersive!", "A true classic of cosmic horror."),
                1 to listOf("Incredible world-building!", "The best sci-fi I have ever read."),
                2 to listOf("A masterpiece of mystery.", "I never saw the twist coming!"),
            )
    }
}
