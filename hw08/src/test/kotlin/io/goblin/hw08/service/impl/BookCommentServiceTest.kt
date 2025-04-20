package io.goblin.hw08.service.impl

import io.goblin.hw08.mapper.toDto
import io.goblin.hw08.model.Book
import io.goblin.hw08.model.BookComment
import io.goblin.hw08.service.BookCommentService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.ActiveProfiles

@DataMongoTest
@Import(BookCommentServiceImpl::class)
@ActiveProfiles("test")
class BookCommentServiceTest {
    @Autowired
    private lateinit var service: BookCommentService

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    private lateinit var testBookId: String
    private lateinit var testCommentIds: List<String>

    @BeforeEach
    fun setup() {
        val book =
            mongoTemplate.findAll(Book::class.java).firstOrNull()
                ?: error("No books found in test DB")
        testBookId = requireNotNull(book.id)

        testCommentIds =
            mongoTemplate
                .find(Query(Criteria.where("bookId").`is`(testBookId)), BookComment::class.java)
                .mapNotNull { it.id }
    }

    @Test
    fun `should return correct book comment by id`() {
        val commentId = testCommentIds.first()
        val expected = mongoTemplate.findById(commentId, BookComment::class.java)?.toDto()

        val actual = service.findById(commentId)

        assertThat(actual)
            .isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(expected)
    }

    @Test
    fun `should return correct book comments by book id`() {
        val expected =
            mongoTemplate
                .find(Query(Criteria.where("bookId").`is`(testBookId)), BookComment::class.java)
                .map { it.toDto() }

        val actual = service.findByBookId(testBookId)

        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected)
    }

    @Test
    fun `should save new book comment`() {
        val text = "Absolutely phenomenal book!"

        val result = service.create(text, testBookId)

        assertThat(result.id).isNotNull()

        val persisted = mongoTemplate.findById(result.id, BookComment::class.java)?.toDto()
        assertThat(persisted?.text).isEqualTo(text)
        assertThat(persisted?.bookId).isEqualTo(testBookId)
    }

    @Test
    fun `should update book comment`() {
        val existingId = testCommentIds.first()
        val updatedText = "Updated review text"

        val result = service.update(existingId, updatedText)

        val updated = mongoTemplate.findById(existingId, BookComment::class.java)?.toDto()
        assertThat(updated?.text).isEqualTo(updatedText)
        assertThat(result.text).isEqualTo(updatedText)
    }

    @Test
    fun `should delete comment`() {
        val commentId = testCommentIds.first()

        assertThat(mongoTemplate.findById(commentId, BookComment::class.java)).isNotNull()

        service.deleteById(commentId)

        assertThat(mongoTemplate.findById(commentId, BookComment::class.java)).isNull()
    }
}
