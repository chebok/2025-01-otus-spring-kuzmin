package io.goblin.hw11.http.controller

import com.ninjasquad.springmockk.MockkBean
import io.goblin.hw11.dto.AuthorDto
import io.goblin.hw11.dto.BookDto
import io.goblin.hw11.dto.GenreDto
import io.goblin.hw11.service.BookService
import io.mockk.coEvery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.Test

@WebFluxTest(BookController::class)
class BookControllerWebFluxTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockkBean
    private lateinit var bookService: BookService

    @Test
    fun `should return book by id`() {
        val bookId = 1L
        val bookDto =
            BookDto(
                id = bookId,
                title = "Test Book",
                author =
                    AuthorDto(
                        id = 1,
                        fullName = "Test Author",
                    ),
                genres = listOf(GenreDto(id = 1, name = "Science")),
            )

        coEvery { bookService.findById(bookId) } returns bookDto

        webTestClient
            .get()
            .uri("/books/$bookId")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(BookDto::class.java)
            .value { body ->
                assert(body.id == bookId)
                assert(body.title == "Test Book")
            }
    }
}
