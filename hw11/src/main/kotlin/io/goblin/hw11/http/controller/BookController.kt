package io.goblin.hw11.http.controller

import io.goblin.hw11.dto.BookDto
import io.goblin.hw11.dto.CreateBookRequest
import io.goblin.hw11.dto.UpdateBookRequest
import io.goblin.hw11.service.BookService
import io.goblin.hw11.service.command.CreateBookCommand
import io.goblin.hw11.service.command.UpdateBookCommand
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class BookController(
    private val bookService: BookService,
) {
    @GetMapping("/books")
    suspend fun getAllBooks(): List<BookDto> = bookService.findAll()

    @GetMapping("/books/{id}")
    suspend fun getBookById(
        @PathVariable("id") id: Long,
    ): BookDto = bookService.findById(id)

    @PostMapping("/books")
    suspend fun createBook(
        @Valid @RequestBody bookRequest: CreateBookRequest,
    ): BookDto =
        bookService.create(
            CreateBookCommand(
                title = bookRequest.title,
                authorId = bookRequest.authorId,
                genreIds = bookRequest.genreIds,
            ),
        )

    @PutMapping("/books/{id}")
    suspend fun updateBook(
        @PathVariable("id") id: Long,
        @Valid @RequestBody bookRequest: UpdateBookRequest,
    ): BookDto =
        bookService.update(
            UpdateBookCommand(
                id = id,
                title = bookRequest.title,
                authorId = bookRequest.authorId,
                genreIds = bookRequest.genreIds,
            ),
        )

    @DeleteMapping("/books/{id}")
    suspend fun deleteBook(
        @PathVariable("id") id: Long,
    ) {
        bookService.deleteById(id)
    }
}
