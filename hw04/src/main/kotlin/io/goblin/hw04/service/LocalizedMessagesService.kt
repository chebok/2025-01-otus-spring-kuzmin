package io.goblin.hw04.service

interface LocalizedMessagesService {
    fun getMessage(
        code: String,
        vararg args: Any,
    ): String
}
