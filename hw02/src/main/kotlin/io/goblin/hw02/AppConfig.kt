package io.goblin.hw02

import io.goblin.hw02.config.AppProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("io.goblin.hw02")
class AppConfig {
    @Bean
    fun appProperties(
        @Value("\${test.fileName}") testFileName: String,
        @Value("\${test.rightAnswersCountToPass}") rightAnswersCountToPass: Int,
    ): AppProperties = AppProperties(rightAnswersCountToPass, testFileName)
}
