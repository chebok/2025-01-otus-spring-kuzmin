package io.goblin.hw11.http.controller

import io.goblin.hw11.PostgresEnvironmentInitializer
import io.goblin.hw11.dto.BookDto
import io.goblin.hw11.dto.CreateBookRequest
import io.goblin.hw11.dto.UpdateBookRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [PostgresEnvironmentInitializer::class])
class BookControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `should perform full CRUD flow for book`() {
        // Create a book
        val createRequest =
            CreateBookRequest(
                title = "Kotlin in Action",
                authorId = 1L,
                genreIds = setOf(1L, 2L, 3L),
            )

        val createdBook =
            webTestClient
                .post()
                .uri("/books")
                .bodyValue(createRequest)
                .exchange()
                .expectStatus()
                .isOk
                .expectBody(BookDto::class.java)
                .returnResult()
                .responseBody!!

        assert(createdBook.id != null) { "Created book should have an ID" }
        assert(createdBook.title == createRequest.title) { "Created book title mismatch" }

        // Get the created book
        val fetchedBook =
            webTestClient
                .get()
                .uri("/books/${createdBook.id}")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody(BookDto::class.java)
                .returnResult()
                .responseBody!!

        assert(fetchedBook.id == createdBook.id) { "Fetched book ID mismatch" }
        assert(fetchedBook.title == "Kotlin in Action") { "Fetched book title mismatch" }
        assert(fetchedBook.genres.size == 3) { "Expected 3 genres, got ${fetchedBook.genres.size}" }
        val expectedGenre = "Comedy, \"Romantic\""
        val hasExpectedGenre = fetchedBook.genres.any { it.name == expectedGenre }

        assert(hasExpectedGenre) { "Expected genre '$expectedGenre' not found in ${fetchedBook.genres.map { it.name }}" }

        // Update the book
        val updateRequest =
            UpdateBookRequest(
                title = "Kotlin in Depth",
                authorId = 2L,
                genreIds = setOf(2L),
            )

        val updatedBook =
            webTestClient
                .put()
                .uri("/books/${createdBook.id}")
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus()
                .isOk
                .expectBody(BookDto::class.java)
                .returnResult()
                .responseBody!!

        assert(updatedBook.title == updateRequest.title) { "Updated book title mismatch" }
        assert(updatedBook.author.id == updateRequest.authorId) { "Updated book author mismatch" }

        // Delete the book
        webTestClient
            .delete()
            .uri("/books/${createdBook.id}")
            .exchange()
            .expectStatus()
            .isOk

        // Verify that the book is deleted
        webTestClient
            .get()
            .uri("/books/${createdBook.id}")
            .exchange()
            .expectStatus()
            .isNotFound
    }

    @Test
    fun `should return 404 when getting non-existing book`() {
        val nonExistingId = Long.MAX_VALUE

        webTestClient
            .get()
            .uri("/books/$nonExistingId")
            .exchange()
            .expectStatus()
            .isNotFound
    }

    @Test
    fun `should return 404 when creating book with non-existing author or genre`() {
        val createRequest =
            CreateBookRequest(
                title = "Invalid Book",
                authorId = Long.MAX_VALUE,
                genreIds = setOf(Long.MAX_VALUE),
            )

        webTestClient
            .post()
            .uri("/books")
            .bodyValue(createRequest)
            .exchange()
            .expectStatus()
            .isNotFound
    }

    @Test
    fun `should return 404 when updating non-existing book`() {
        val updateRequest =
            UpdateBookRequest(
                title = "Invalid Update",
                authorId = 1L,
                genreIds = setOf(1L),
            )

        webTestClient
            .put()
            .uri("/books/999")
            .bodyValue(updateRequest)
            .exchange()
            .expectStatus()
            .isNotFound
    }
}
