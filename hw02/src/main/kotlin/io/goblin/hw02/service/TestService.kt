package io.goblin.hw02.service

import io.goblin.hw02.domain.Student
import io.goblin.hw02.domain.TestResult

interface TestService {
    fun executeTestFor(student: Student): TestResult
}
