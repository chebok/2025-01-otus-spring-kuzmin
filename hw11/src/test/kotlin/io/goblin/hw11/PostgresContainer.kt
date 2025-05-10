package io.goblin.hw11

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import org.testcontainers.containers.PostgreSQLContainer

class PostgresEnvironmentInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(context: ConfigurableApplicationContext) {
        val postgresContainer =
            PostgreSQLContainer<Nothing>("postgres:16").apply {
                withDatabaseName("testdb")
                withUsername("test")
                withPassword("test")
                start()
            }

        val environment = context.environment as ConfigurableEnvironment
        val datasourceProps =
            mapOf(
                "spring.r2dbc.url" to
                    "r2dbc:postgresql://${postgresContainer.host}:${postgresContainer.firstMappedPort}/${postgresContainer.databaseName}",
                "spring.r2dbc.username" to postgresContainer.username,
                "spring.r2dbc.password" to postgresContainer.password,
                "spring.flyway.url" to
                    "jdbc:postgresql://${postgresContainer.host}:${postgresContainer.firstMappedPort}/${postgresContainer.databaseName}",
                "spring.flyway.user" to postgresContainer.username,
                "spring.flyway.password" to postgresContainer.password,
            )

        environment.propertySources.addFirst(MapPropertySource("testcontainers", datasourceProps))
    }
}
