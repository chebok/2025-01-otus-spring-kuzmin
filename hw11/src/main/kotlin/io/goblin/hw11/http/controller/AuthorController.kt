package io.goblin.hw11.http.controller

import io.goblin.hw11.dto.AuthorDto
import io.goblin.hw11.service.AuthorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorController(
    private val authorService: AuthorService,
) {
    @GetMapping("/authors")
    suspend fun getAllAuthors(): List<AuthorDto> = authorService.findAll()
}
