package io.goblin.hw05.cli

import io.goblin.hw05.converters.GenreConverter
import io.goblin.hw05.service.GenreService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class GenreCommands(
    private val genreService: GenreService,
    private val genreConverter: GenreConverter,
) {
    @ShellMethod("Find all genres", key = ["ag", "genres"])
    fun findAllAuthors(): String =
        genreService
            .findAll()
            .map { genreConverter.genreToString(it) }
            .joinToString(",\n") { it }
}
