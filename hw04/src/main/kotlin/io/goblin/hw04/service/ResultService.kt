package io.goblin.hw04.service

import io.goblin.hw04.domain.TestResult

interface ResultService {
    fun showResult(testResult: TestResult)
}
