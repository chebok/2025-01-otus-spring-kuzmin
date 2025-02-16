package io.goblin.hw02.service

import io.goblin.hw02.domain.Student

interface StudentService {
    fun determineCurrentStudent(): Student
}
