package io.goblin.hw01.config

data class AppProperties(
    override val testFileName: String,
) : TestFileNameProvider
