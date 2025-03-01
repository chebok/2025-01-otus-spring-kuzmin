package io.goblin.hw04.cli

import io.goblin.hw04.service.TestRunnerService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class TestShellCommands(
    private val testRunnerService: TestRunnerService,
) {
    @ShellMethod("Run app", key = ["run"])
    fun runApp() {
        testRunnerService.run(emptyArray())
    }
}
