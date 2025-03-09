package io.goblin.hw04.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.*

@ConfigurationProperties(prefix = "test")
data class AppProperties(
    override val rightAnswersCountToPass: Int,
    val fileNameByLocaleTag: Map<String, String>,
    override val locale: Locale,
) : TestConfig,
    TestFileNameProvider,
    LocaleConfig {
    override val testFileName: String
        get() =
            fileNameByLocaleTag[locale.toLanguageTag()]
                ?: throw IllegalArgumentException("There is no questions for locale ${locale.toLanguageTag()}")
}
