package io.goblin.hw03.service

import io.goblin.hw03.config.properties.LocaleConfig
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class LocalizedMessagesServiceImpl(
    private val messageSource: MessageSource,
    private val localeConfig: LocaleConfig,
) : LocalizedMessagesService {
    override fun getMessage(
        code: String,
        vararg args: Any,
    ): String = messageSource.getMessage(code, args, localeConfig.locale)
}
