package io.goblin.hw10.http.controller

import io.goblin.hw10.dto.BookCommentDto
import io.goblin.hw10.dto.CreateBookCommentRequest
import io.goblin.hw10.dto.UpdateBookCommentRequest
import io.goblin.hw10.service.BookCommentService
import io.goblin.hw10.service.command.CreateBookCommentCommand
import io.goblin.hw10.service.command.UpdateBookCommentCommand
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book-comments")
class BookCommentController(
    private val bookCommentService: BookCommentService,
) {
    @GetMapping("/by-book/{bookId}")
    fun getBookCommentsByBookId(
        @PathVariable bookId: Long,
    ): List<BookCommentDto> = bookCommentService.findByBookId(bookId)

    @GetMapping("/{id}")
    fun getBookCommentById(
        @PathVariable id: Long,
    ): BookCommentDto = bookCommentService.findById(id)

    @PostMapping
    fun createComment(
        @Valid @RequestBody bookCommentRequest: CreateBookCommentRequest,
    ): BookCommentDto =
        bookCommentService.create(
            CreateBookCommentCommand(
                text = bookCommentRequest.text,
                bookId = bookCommentRequest.bookId,
            ),
        )

    @PutMapping("/{id}")
    fun updateComment(
        @PathVariable id: Long,
        @Valid @RequestBody bookCommentRequest: UpdateBookCommentRequest,
    ): BookCommentDto =
        bookCommentService.update(
            UpdateBookCommentCommand(
                id = id,
                text = bookCommentRequest.text,
            ),
        )

    @DeleteMapping("/{id}")
    fun deleteComment(
        @PathVariable id: Long,
    ) {
        bookCommentService.deleteById(id)
    }
}
