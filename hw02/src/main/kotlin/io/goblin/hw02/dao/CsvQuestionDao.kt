package io.goblin.hw02.dao

import com.opencsv.bean.CsvToBeanBuilder
import io.goblin.hw02.config.TestFileNameProvider
import io.goblin.hw02.dao.dto.QuestionDto
import io.goblin.hw02.domain.Question
import org.springframework.stereotype.Repository
import java.io.InputStreamReader

@Repository
class CsvQuestionDao(
    private val fileNameProvider: TestFileNameProvider,
) : QuestionDao {
    override fun findAll(): List<Question> {
        val fileName = fileNameProvider.testFileName
        val inputStream =
            javaClass.classLoader.getResourceAsStream(fileName)
                ?: throw IllegalArgumentException("File $fileName not found")

        return inputStream.use {
            CsvToBeanBuilder<QuestionDto>(InputStreamReader(it))
                .withType(QuestionDto::class.java)
                .withSkipLines(1)
                .withSeparator(';')
                .build()
                .parse()
                .map { dto -> dto.toDomainObject() }
        }
    }
}
