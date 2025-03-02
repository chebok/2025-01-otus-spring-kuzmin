package io.goblin.hw01.service

import java.io.PrintStream

class StreamsIOService(
    private val printStream: PrintStream,
) : IOService {
    override fun printLine(s: String) {
        printStream.println(s)
    }

    override fun printFormattedLine(
        s: String,
        vararg args: Any,
    ) {
        printStream.printf("$s\n", *args)
    }
}
