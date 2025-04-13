package io.goblin.hw08.cli

import io.goblin.hw08.converters.BookConverter
import io.goblin.hw08.service.BookService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class BookCommands(
    private val bookService: BookService,
    private val bookConverter: BookConverter,
) {
    @ShellMethod("Find all books", key = ["ab", "books"])
    fun findAllBooks(): String =
        bookService
            .findAll()
            .map { bookConverter.bookToString(it) }
            .joinToString(",\n") { it }

    @ShellMethod("Find book by id", key = ["bbid", "find-book"])
    fun findBookById(
        @ShellOption("--id", "-i") id: String,
    ): String =
        bookService.findById(id)?.let {
            bookConverter.bookToString(it)
        } ?: "Book with id $id not found"

    @ShellMethod("Delete book by id", key = ["bdel", "delete-book"])
    fun deleteBook(
        @ShellOption("--id", "-i") id: String,
    ) {
        bookService.deleteById(id)
    }

    @ShellMethod("Insert book", key = ["bins", "insert-book"])
    fun insertBook(
        @ShellOption("--title", "-t") title: String,
        @ShellOption("--author-id", "-a") authorId: String,
        @ShellOption("--genres-ids", "-g") genresIds: Set<String>,
    ): String {
        val savedBook = bookService.create(title, authorId, genresIds)
        return bookConverter.bookToString(savedBook)
    }

    @ShellMethod("Update book", key = ["bupd", "update-book"])
    fun updateBook(
        @ShellOption("--id", "-i") id: String,
        @ShellOption("--title", "-t") title: String,
        @ShellOption("--author-id", "-a") authorId: String,
        @ShellOption("--genres-ids", "-g") genresIds: Set<String>,
    ): String {
        val savedBook = bookService.update(id, title, authorId, genresIds)
        return bookConverter.bookToString(savedBook)
    }
}
