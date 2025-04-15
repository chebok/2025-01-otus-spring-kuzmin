package io.goblin.hw10.http.controller

import io.goblin.hw10.dto.BookDto
import io.goblin.hw10.dto.CreateBookRequest
import io.goblin.hw10.dto.UpdateBookRequest
import io.goblin.hw10.service.BookService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class BookController(
    private val bookService: BookService,
) {
    @GetMapping("/books")
    fun getAllBooks(): List<BookDto> = bookService.findAll()

    @GetMapping("/books/{id}")
    fun getBookById(
        @PathVariable("id") id: Long,
    ): BookDto = bookService.findById(id)

    @PostMapping("/books")
    fun createBook(
        @Valid @RequestBody bookRequest: CreateBookRequest,
    ): BookDto = bookService.create(bookRequest)

    @PutMapping("/books/{id}")
    fun updateBook(
        @PathVariable("id") id: Long,
        @Valid @RequestBody bookRequest: UpdateBookRequest,
    ): BookDto = bookService.update(id, bookRequest)

    @DeleteMapping("/books/{id}")
    fun deleteBook(
        @PathVariable("id") id: Long,
    ) {
        bookService.deleteById(id)
    }
}
