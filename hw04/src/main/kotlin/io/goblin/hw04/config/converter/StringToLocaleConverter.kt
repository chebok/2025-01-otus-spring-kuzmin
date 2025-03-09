package io.goblin.hw04.config.converter

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.util.Locale

@Component
@ConfigurationPropertiesBinding
class StringToLocaleConverter : Converter<String, Locale> {
    override fun convert(source: String): Locale = Locale.forLanguageTag(source)
}
