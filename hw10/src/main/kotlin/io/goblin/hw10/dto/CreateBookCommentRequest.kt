package io.goblin.hw10.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateBookCommentRequest(
    @field:NotNull(message = "BookId required")
    val bookId: Long,
    @NotBlank(message = "Text cannot be empty")
    val text: String,
)
