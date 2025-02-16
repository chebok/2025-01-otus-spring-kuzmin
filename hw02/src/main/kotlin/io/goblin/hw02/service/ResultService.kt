package io.goblin.hw02.service

import io.goblin.hw02.domain.TestResult

interface ResultService {
    fun showResult(testResult: TestResult)
}
