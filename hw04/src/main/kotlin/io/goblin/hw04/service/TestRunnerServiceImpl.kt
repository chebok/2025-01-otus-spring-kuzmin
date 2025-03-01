package io.goblin.hw04.service

import org.springframework.stereotype.Service

@Service
class TestRunnerServiceImpl(
    private val testService: TestService,
    private val studentService: StudentService,
    private val resultService: ResultService,
) : TestRunnerService {
    override fun run(args: Array<String>) {
        studentService
            .determineCurrentStudent()
            .let { testService.executeTestFor(it) }
            .also { resultService.showResult(it) }
    }
}
