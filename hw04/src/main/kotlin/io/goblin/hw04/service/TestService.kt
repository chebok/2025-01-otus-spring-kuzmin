package io.goblin.hw04.service

import io.goblin.hw04.domain.Student
import io.goblin.hw04.domain.TestResult

interface TestService {
    fun executeTestFor(student: Student): TestResult
}
