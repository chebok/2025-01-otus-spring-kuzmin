package io.goblin.hw03.service

import io.goblin.hw03.domain.Student
import io.goblin.hw03.domain.TestResult

interface TestService {
    fun executeTestFor(student: Student): TestResult
}
