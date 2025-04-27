package io.goblin.hw11.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CreateBookRequest(
    @field:NotBlank(message = "Book title cannot be empty")
    val title: String,
    @field:NotNull(message = "Author required")
    val authorId: Long,
    @field:NotEmpty(message = "At least one genre must be selected")
    val genreIds: Set<Long>,
)
