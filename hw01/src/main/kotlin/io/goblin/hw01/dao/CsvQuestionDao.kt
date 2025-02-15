package io.goblin.hw01.dao

import com.opencsv.bean.CsvToBeanBuilder
import io.goblin.hw01.config.TestFileNameProvider
import io.goblin.hw01.dao.dto.QuestionDto
import io.goblin.hw01.domain.Question
import java.io.InputStreamReader

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
