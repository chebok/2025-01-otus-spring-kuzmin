package io.goblin.hw03.domain

data class Question(
    val text: String,
    val answers: List<Answer>,
)
