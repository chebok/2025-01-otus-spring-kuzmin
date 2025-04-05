package io.goblin.hw08.cli

import io.goblin.hw08.converters.AuthorConverter
import io.goblin.hw08.service.AuthorService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class AuthorCommands(
    private val authorService: AuthorService,
    private val authorConverter: AuthorConverter,
) {
    @ShellMethod("Find all authors", key = ["aa", "authors"])
    fun findAllAuthors(): String =
        authorService
            .findAll()
            .map { authorConverter.authorToString(it) }
            .joinToString(",\n") { it }
}
