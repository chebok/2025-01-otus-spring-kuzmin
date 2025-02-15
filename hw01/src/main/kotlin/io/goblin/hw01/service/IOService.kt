package io.goblin.hw01.service

interface IOService {
    fun printLine(s: String)

    fun printFormattedLine(
        s: String,
        vararg args: Any,
    )
}
