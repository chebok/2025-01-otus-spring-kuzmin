package io.goblin.hw10.http

data class ErrorResponse(
    val status: Int,
    val errors: Map<String, String>,
)
