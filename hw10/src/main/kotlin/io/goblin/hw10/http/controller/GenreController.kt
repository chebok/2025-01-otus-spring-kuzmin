package io.goblin.hw10.http.controller

import io.goblin.hw10.dto.GenreDto
import io.goblin.hw10.service.GenreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GenreController(
    private val genreService: GenreService,
) {
    @GetMapping("/genres")
    fun getAllGenres(): List<GenreDto> = genreService.findAll()
}
