package io.goblin.hw03.service

import io.goblin.hw03.domain.Student

interface StudentService {
    fun determineCurrentStudent(): Student
}
