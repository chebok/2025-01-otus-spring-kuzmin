package io.goblin.hw03.service

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.Scanner

@Service
@Primary
class StreamsIOService : IOService {
    private val printStream = System.out
    private val scanner = Scanner(System.`in`)

    override fun printLine(s: String) {
        printStream.println(s)
    }

    override fun printFormattedLine(
        s: String,
        vararg args: Any,
    ) {
        printStream.printf("$s\n", *args)
    }

    override fun readString(): String = scanner.nextLine()

    override fun readStringWithPrompt(prompt: String): String {
        printLine(prompt)
        return scanner.nextLine()
    }

    override fun readIntForRange(
        min: Int,
        max: Int,
        errorMessage: String,
    ): Int {
        repeat(MAX_ATTEMPTS) {
            readString()
                .toIntOrNull()
                ?.takeIf { it in min..max }
                ?.let { return it }
            printLine(errorMessage)
        }
        throw IllegalArgumentException("Error during reading int value")
    }

    override fun readIntForRangeWithPrompt(
        min: Int,
        max: Int,
        prompt: String,
        errorMessage: String,
    ): Int {
        printLine(prompt)
        return readIntForRange(min, max, errorMessage)
    }

    companion object {
        private const val MAX_ATTEMPTS = 10
    }
}
