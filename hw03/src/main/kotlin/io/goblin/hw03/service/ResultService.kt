package io.goblin.hw03.service

import io.goblin.hw03.domain.TestResult

interface ResultService {
    fun showResult(testResult: TestResult)
}
