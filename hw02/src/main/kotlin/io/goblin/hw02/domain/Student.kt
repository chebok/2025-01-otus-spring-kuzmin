package io.goblin.hw02.domain

data class Student(
    val firstName: String,
    val lastName: String,
    val fullName: String = "$firstName $lastName",
)
