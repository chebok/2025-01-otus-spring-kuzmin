package io.goblin.hw11.http

data class ErrorResponse(
    val status: Int,
    val errors: Map<String, String>,
)
