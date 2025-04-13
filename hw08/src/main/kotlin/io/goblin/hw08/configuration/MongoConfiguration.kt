package io.goblin.hw08.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["io.goblin.hw08.persistence.repository"])
class MongoConfiguration
