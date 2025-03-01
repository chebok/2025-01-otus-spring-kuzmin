package io.goblin.hw04.domain

data class Question(
    val text: String,
    val answers: List<Answer>,
)
