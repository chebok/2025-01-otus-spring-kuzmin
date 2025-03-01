package io.goblin.hw03.service

import io.goblin.hw03.domain.Student
import org.springframework.stereotype.Service

@Service
class StudentServiceImpl(
    private val ioService: LocalizedIOService,
) : StudentService {
    override fun determineCurrentStudent(): Student {
        val firstName = ioService.readStringWithPromptLocalized(INPUT_FIRST_NAME_CODE)
        val lastName = ioService.readStringWithPromptLocalized(INPUT_LAST_NAME_CODE)
        return Student(firstName, lastName)
    }

    companion object {
        private const val INPUT_FIRST_NAME_CODE = "studentService.input.first.name"
        private const val INPUT_LAST_NAME_CODE = "studentService.input.last.name"
    }
}
