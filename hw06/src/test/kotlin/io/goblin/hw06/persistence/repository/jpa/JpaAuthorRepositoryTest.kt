package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.persistence.entity.AuthorEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(JpaAuthorRepository::class)
class JpaAuthorRepositoryTest {
    @Autowired
    private lateinit var em: TestEntityManager

    @Autowired
    private lateinit var authorRepository: JpaAuthorRepository

    @Test
    fun `should return correct author list`() {
        val expectedAuthors = authorIds.map { em.find(AuthorEntity::class.java, it) }
        val actualAuthors = authorRepository.findAll()
        actualAuthors.forEachIndexed { i, author ->
            assertThat(author).usingRecursiveComparison().isEqualTo(expectedAuthors[i])
        }
    }

    @ParameterizedTest(name = "should return correct author by id {0}")
    @ValueSource(longs = [1L, 2L, 3L])
    fun `should return correct author by id`(expectedAuthorId: Long) {
        val expectedAuthor = em.find(AuthorEntity::class.java, expectedAuthorId)
        val actualAuthor = authorRepository.findById(expectedAuthorId)
        assertThat(actualAuthor)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(expectedAuthor)
    }

    companion object {
        private val authorIds = listOf(1, 2, 3)
    }
}
