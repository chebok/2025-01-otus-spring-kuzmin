package io.goblin.hw01.service

class TestRunnerServiceImpl(
    private val testService: TestService,
) : TestRunnerService {
    override fun run() {
        testService.executeTest()
    }
}
