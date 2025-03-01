package io.goblin.hw03.domain

data class Student(
    val firstName: String,
    val lastName: String,
    val fullName: String = "$firstName $lastName",
)
