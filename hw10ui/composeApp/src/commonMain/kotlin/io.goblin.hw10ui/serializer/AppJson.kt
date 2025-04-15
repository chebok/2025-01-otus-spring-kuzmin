package io.goblin.hw10ui.serializer

import kotlinx.serialization.json.Json

val AppJson =
    Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }
