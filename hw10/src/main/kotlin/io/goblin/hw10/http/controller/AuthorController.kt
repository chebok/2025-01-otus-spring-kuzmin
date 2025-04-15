package io.goblin.hw10.http.controller

import io.goblin.hw10.dto.AuthorDto
import io.goblin.hw10.service.AuthorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorController(
    private val authorService: AuthorService,
) {
    @GetMapping("/authors")
    fun getAllAuthors(): List<AuthorDto> = authorService.findAll()
}
