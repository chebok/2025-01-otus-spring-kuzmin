package io.goblin.hw02.dao.dto

import com.opencsv.bean.AbstractCsvConverter
import io.goblin.hw02.domain.Answer

class AnswerCsvConverter : AbstractCsvConverter() {
    override fun convertToRead(value: String?): Answer {
        val parts = value?.split("%") ?: error("Invalid answer format: $value")
        return Answer(parts[0], parts[1].toBoolean())
    }
}
