package io.goblin.hw06.persistence.repository.jpa

import io.goblin.hw06.model.Genre
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(JpaGenreRepository::class)
class JpaGenreRepositoryTest {
    @Autowired
    private lateinit var em: TestEntityManager

    @Autowired
    private lateinit var genreRepository: JpaGenreRepository

    @Test
    fun `should return correct genre list`() {
        val expectedGenres = genreIds.map { em.find(Genre::class.java, it) }
        val actualGenres = genreRepository.findAll()
        actualGenres.forEachIndexed { i, genre ->
            assertThat(genre).usingRecursiveComparison().isEqualTo(expectedGenres[i])
        }
    }

    @Test
    fun `should return correct genre list by ids`() {
        val expectedGenres = genreIds.map { em.find(Genre::class.java, it) }
        val expectedGenreIds = setOf<Long>(2, 4, 6)
        val expectedGenresByIds = expectedGenres.filter { it.id in expectedGenreIds }
        val actualGenres = genreRepository.findAllByIds(expectedGenreIds)
        actualGenres.forEachIndexed { i, genre ->
            assertThat(genre).usingRecursiveComparison().isEqualTo(expectedGenresByIds[i])
        }
    }

    companion object {
        private val genreIds = listOf(1, 2, 3, 4, 5, 6)
    }
}
