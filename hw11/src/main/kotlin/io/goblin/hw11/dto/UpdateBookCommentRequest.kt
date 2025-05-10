package io.goblin.hw11.dto

import jakarta.validation.constraints.NotBlank

data class UpdateBookCommentRequest(
    @NotBlank(message = "Text cannot be empty")
    val text: String,
)
