package io.goblin.hw11.http.controller

import io.goblin.hw11.dto.GenreDto
import io.goblin.hw11.service.GenreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GenreController(
    private val genreService: GenreService,
) {
    @GetMapping("/genres")
    suspend fun getAllGenres(): List<GenreDto> = genreService.findAll()
}
