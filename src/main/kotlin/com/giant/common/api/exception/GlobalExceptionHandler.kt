package com.giant.common.api.exception

import com.giant.common.api.response.ErrorResponseDto
import com.giant.common.api.response.ResponseDto
import com.giant.common.api.type.ResponseCode
import com.giant.common.api.type.ValidationError
import mu.KotlinLogging
import org.springframework.dao.DataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ResponseDto> {

        val errors = ex.bindingResult.fieldErrors.map {
            ValidationError(
                field = it.field,
                message = it.defaultMessage ?: "Invalid value"
            )
        }

        log.warn { "Validation failed: $errors" }

        return ResponseEntity(
            ErrorResponseDto.WithErrors(
                ResponseCode.VALIDATION_ERROR.code,
                ResponseCode.VALIDATION_ERROR.message,
                errors
            ),
            ResponseCode.VALIDATION_ERROR.status
        )
    }

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException): ResponseEntity<ResponseDto> {
        log.error { "Custom exception occurred: ${ex.responseCode.message}" }
        return ResponseEntity(
            ErrorResponseDto.Simple(
                ex.responseCode.code,
                ex.responseCode.message
            ),
            ex.responseCode.status
        )
    }

    @ExceptionHandler(DataAccessException::class)
    fun handleDatabaseException(ex: DataAccessException): ResponseEntity<ResponseDto> {
        log.error(ex) { "Database exception occurred" }
        return ResponseEntity(
            ErrorResponseDto.Simple(
                ResponseCode.INTERNAL_SERVER_ERROR.code,
                ResponseCode.INTERNAL_SERVER_ERROR.message
            ),
            ResponseCode.INTERNAL_SERVER_ERROR.status
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ResponseDto> {
        log.error(ex) { "Unhandled exception occurred" }
        return ResponseEntity(
            ErrorResponseDto.Simple(
                ResponseCode.INTERNAL_SERVER_ERROR.code,
                ResponseCode.INTERNAL_SERVER_ERROR.message
            ),
            ResponseCode.INTERNAL_SERVER_ERROR.status
        )
    }
}