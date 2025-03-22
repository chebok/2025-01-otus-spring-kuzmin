package io.goblin.hw06.cli

import io.goblin.hw06.converters.GenreConverter
import io.goblin.hw06.service.GenreService
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
