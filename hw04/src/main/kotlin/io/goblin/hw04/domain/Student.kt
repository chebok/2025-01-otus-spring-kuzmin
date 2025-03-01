package io.goblin.hw04.domain

data class Student(
    val firstName: String,
    val lastName: String,
    val fullName: String = "$firstName $lastName",
)
