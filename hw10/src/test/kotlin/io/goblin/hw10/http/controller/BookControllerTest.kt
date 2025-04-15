package io.goblin.hw10.http.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.goblin.hw10.dto.*
import io.goblin.hw10.exceptions.EntityNotFoundException
import io.goblin.hw10.http.ErrorResponse
import io.goblin.hw10.service.BookService
import io.mockk.every
import io.mockk.justRun
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import java.util.stream.Stream

@WebMvcTest(BookController::class)
class BookControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var bookService: BookService

    @Test
    fun getAllBooks() {
        every { bookService.findAll() } returns listOf(book)
        val response =
            mockMvc
                .get("/books")
                .andExpect {
                    status {
                        isOk()
                    }
                }.andReturn()
                .response
        val books = jacksonObjectMapper().readValue<List<BookDto>>(response.contentAsString)
        assertThat(books).hasSize(1)
        assertThat(books[0]).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun getBookById() {
        every { bookService.findById(book.id) } returns book
        val response =
            mockMvc
                .get("/books/${book.id}")
                .andExpect {
                    status {
                        isOk()
                    }
                }.andReturn()
                .response
        val actualBook = jacksonObjectMapper().readValue<BookDto>(response.contentAsString)
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun createBook() {
        every { bookService.create(any()) } returns book
        val response =
            mockMvc
                .perform(
                    post("/books")
                        .content(createRequest.objToString())
                        .contentType(MediaType.APPLICATION_JSON),
                ).andReturn()
                .response
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.contentAsString.toObj<BookDto>()).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun updateBook() {
        every { bookService.update(any(), any()) } returns book
        val response =
            mockMvc
                .perform(
                    put("/books/${book.id}")
                        .content(updateRequest.objToString())
                        .contentType(MediaType.APPLICATION_JSON),
                ).andReturn()
                .response
        assertThat(response.status).isEqualTo(HttpStatus.OK.value())
        assertThat(response.contentAsString.toObj<BookDto>()).usingRecursiveComparison().isEqualTo(book)
    }

    @Test
    fun deleteBook() {
        val slot = slot<Long>()
        justRun { bookService.deleteById(capture(slot)) }
        val status =
            mockMvc
                .perform(delete("/books/${book.id}"))
                .andReturn()
                .response.status
        assertThat(status).isEqualTo(HttpStatus.OK.value())
        assertThat(slot.captured).isEqualTo(book.id)
    }

    @ParameterizedTest(name = "validation error")
    @MethodSource("requestProvider")
    fun bookRequestValidation(
        request: CreateBookRequest,
        errorMessage: Pair<String, String>,
    ) {
        val response =
            mockMvc
                .perform(
                    post("/books")
                        .content(request.objToString())
                        .contentType(MediaType.APPLICATION_JSON),
                ).andReturn()
                .response
        val restApiError = response.contentAsString.toObj<ErrorResponse>()
        assertThat(response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(restApiError.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(restApiError.errors[errorMessage.first]).isEqualTo(errorMessage.second)
    }

    @Test
    fun getNonExistingBook() {
        val bookId = 4L
        val errorMessage = "Book not found"
        every { bookService.findById(bookId) } throws EntityNotFoundException(errorMessage)
        val response = mockMvc.get("/books/$bookId").andReturn().response
        val restApiError = response.contentAsString.toObj<ErrorResponse>()
        assertThat(response.status).isEqualTo(HttpStatus.NOT_FOUND.value())
        assertThat(restApiError.status).isEqualTo(HttpStatus.NOT_FOUND.value())
        assertThat(restApiError.errors["message"]).isEqualTo(errorMessage)
    }

    companion object {
        private val author = AuthorDto(1L, randomString())
        private val genre = GenreDto(1L, randomString())
        private val book =
            BookDto(
                id = 1L,
                title = randomString(),
                author = author,
                genres = listOf(genre),
            )
        private val createRequest =
            UpdateBookRequest(
                book.title,
                author.id,
                setOf(genre.id),
            )
        private val updateRequest =
            CreateBookRequest(
                book.title,
                author.id,
                setOf(genre.id),
            )

        private fun randomString(length: Int = 10): String =
            (1..length)
                .map { ('a'..'z').random() }
                .joinToString("")

        fun <T> T.objToString(): String = jacksonObjectMapper().writeValueAsString(this)

        inline fun <reified T> String.toObj(): T = jacksonObjectMapper().readValue<T>(this)

        @JvmStatic
        fun requestProvider(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    CreateBookRequest(title = "", authorId = author.id, genreIds = setOf(genre.id)),
                    "title" to "Book title cannot be empty",
                ),
                Arguments.of(
                    CreateBookRequest(title = book.title, authorId = author.id, genreIds = emptySet()),
                    "genreIds" to "At least one genre must be selected",
                ),
            )
    }
}
