package io.goblin.hw02.service

import io.goblin.hw02.domain.Student
import org.springframework.stereotype.Service

@Service
class StudentServiceImpl(
    private val ioService: IOService,
) : StudentService {
    override fun determineCurrentStudent(): Student {
        val firstName = ioService.readStringWithPrompt("Please input your first name")
        val lastName = ioService.readStringWithPrompt("Please input your last name")
        return Student(firstName, lastName)
    }
}
