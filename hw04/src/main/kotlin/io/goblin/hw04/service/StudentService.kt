package io.goblin.hw04.service

import io.goblin.hw04.domain.Student

interface StudentService {
    fun determineCurrentStudent(): Student
}
