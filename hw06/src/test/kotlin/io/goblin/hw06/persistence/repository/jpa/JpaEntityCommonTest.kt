package io.goblin.hw06.persistence.repository.jpa

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToOne
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.reflections.Reflections
import java.lang.reflect.Field

class JpaEntityCommonTest {
    @ParameterizedTest
    @MethodSource("getEntities")
    fun `should not have OneToOne relationships`(entityClass: Class<*>) {
        val hasOneToOne = entityClass.declaredFields.any { it.isAnnotationPresent(OneToOne::class.java) }
        assertThat(hasOneToOne)
            .withFailMessage("In the hibernate entities, OneToOne relationships are not allowed.")
            .isFalse
    }

    @ParameterizedTest
    @MethodSource("getEntities")
    fun `should not have EAGER relationships`(entityClass: Class<*>) {
        val hasEagerFetch =
            entityClass.declaredFields
                .mapNotNull { it.getRelationAnnotationArgumentValue("fetch", FetchType::class.java) }
                .any { it == FetchType.EAGER }

        assertThat(hasEagerFetch)
            .withFailMessage("It's better to make all relationships LAZY.")
            .isFalse
    }

    private fun <T> Field.getRelationAnnotationArgumentValue(
        argumentName: String,
        returnType: Class<T>,
    ): T? =
        annotations
            .asSequence()
            .flatMap {
                it.javaClass.declaredMethods
                    .asSequence()
                    .map { method -> method to it }
            }.find { it.first.name == argumentName }
            ?.let { it.first.invoke(it.second) as? T }

    companion object {
        private lateinit var entitiesClasses: Set<Class<*>>

        @JvmStatic
        @BeforeAll
        fun setUpAll() {
            val reflections = Reflections("io.goblin.hw06.persistence.entity")
            entitiesClasses = reflections.getTypesAnnotatedWith(Entity::class.java)
        }

        @JvmStatic
        fun getEntities() = entitiesClasses.stream()
    }
}
