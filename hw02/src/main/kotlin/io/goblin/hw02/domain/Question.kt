package io.goblin.hw02.domain

data class Question(
    val text: String,
    val answers: List<Answer>,
)
