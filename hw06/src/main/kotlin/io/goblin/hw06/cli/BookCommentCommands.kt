package io.goblin.hw06.cli

import io.goblin.hw06.converters.BookCommentConverter
import io.goblin.hw06.service.BookCommentService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class BookCommentCommands(
    private val bookCommentService: BookCommentService,
    private val bookCommentConverter: BookCommentConverter,
) {
    @ShellMethod("Find comments by book id", key = ["cbb", "find-comments" ])
    fun findCommentsByBookId(
        @ShellOption("--book-id", "-b") bookId: Long,
    ): String {
        val comments = bookCommentService.findByBookId(bookId)
        return comments
            .map { bookCommentConverter.commentToString(it) }
            .joinToString(
                separator = ",\n",
                prefix = "Book id: $bookId. Comments:\n",
            ) { it }
    }

    @ShellMethod("Find book comment by id", key = ["cbid", "find-comment"])
    fun findCommentById(
        @ShellOption("--id", "-i") id: Long,
    ): String =
        bookCommentService.findById(id)?.let {
            bookCommentConverter.commentToString(it)
        } ?: "Book comment with id $id not found"

    @ShellMethod("Insert book comment", key = ["cins", "insert-comment"])
    fun insertBookComment(
        @ShellOption("--text", "-t") text: String,
        @ShellOption("--book-id", "-b") bookId: Long,
    ): String {
        val comment = bookCommentService.insert(text, bookId)
        return bookCommentConverter.commentToString(comment)
    }

    @ShellMethod("Update book comment", key = ["cupd", "update-comment"])
    fun updateBookComment(
        @ShellOption("--id", "-i") id: Long,
        @ShellOption("--text", "-t") text: String,
        @ShellOption("--book-id", "-b") bookId: Long,
    ): String {
        val comment = bookCommentService.update(id, text, bookId)
        return bookCommentConverter.commentToString(comment)
    }

    @ShellMethod("Delete book comment by id", key = ["cdel", "delete-comment"])
    fun deleteBookComment(
        @ShellOption("--id", "-i") id: Long,
    ) {
        bookCommentService.deleteById(id)
    }
}
