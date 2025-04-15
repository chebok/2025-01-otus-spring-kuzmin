package io.goblin.hw10ui.network

import io.goblin.hw10ui.model.BookDto
import io.goblin.hw10ui.serializer.AppJson
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Response
import kotlin.js.JsString

suspend fun fetchBooks(): List<BookDto> {
    val response =
        window
            .fetch(
                "/api/books",
            ).await<Response>()

    val jsonString: JsString = response.text().await()
    return AppJson.decodeFromString<List<BookDto>>(jsonString.toString())
}
