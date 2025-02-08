package io.goblin.hw01.dao.dto

import com.opencsv.bean.CsvBindAndSplitByPosition
import com.opencsv.bean.CsvBindByPosition
import io.goblin.hw01.domain.Answer
import io.goblin.hw01.domain.Question

data class QuestionDto(
    @CsvBindByPosition(position = 0)
    var text: String = "",
    @CsvBindAndSplitByPosition(
        position = 1,
        elementType = Answer::class,
        splitOn = "\\|",
        converter = AnswerCsvConverter::class,
    )
    var answers: MutableList<Answer> = mutableListOf(),
) {
    fun toDomainObject(): Question = Question(text, answers)
}
