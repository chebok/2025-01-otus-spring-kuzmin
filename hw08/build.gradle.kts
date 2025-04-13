plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.spring") version "2.0.0"
    kotlin("plugin.jpa") version "2.0.0"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "io.goblin"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("com.github.cloudyrock.mongock:mongock-spring-v5:4.1.13")
    implementation("com.github.cloudyrock.mongock:mongodb-springdata-v3-driver:4.1.13")
    implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:4.11.0")
    implementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x:4.11.0")
    implementation("org.springframework.shell:spring-shell-starter:3.4.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("io.mockk:mockk:1.13.7")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    implementation("org.reflections:reflections:0.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
