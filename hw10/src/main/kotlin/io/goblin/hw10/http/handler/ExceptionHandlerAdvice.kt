package io.goblin.hw10.http.handler

import io.goblin.hw10.exceptions.EntityNotFoundException
import io.goblin.hw10.http.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(e: MethodArgumentNotValidException): ErrorResponse {
        val errors =
            e.bindingResult.allErrors.associate { error ->
                val fieldName = (error as FieldError).field
                val errorMessage = error.getDefaultMessage() ?: "Invalid value"
                fieldName to errorMessage
            }

        return ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            errors = errors,
        )
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFoundException(e: EntityNotFoundException): ErrorResponse =
        ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            errors = mapOf("message" to (e.message ?: "Entity not found")),
        )

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(e: Exception): ErrorResponse {
        e.printStackTrace()
        return ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            errors = mapOf("message" to "Internal server error"),
        )
    }
}
