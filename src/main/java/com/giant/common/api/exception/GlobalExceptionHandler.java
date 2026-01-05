package com.giant.common.api.exception;

import com.giant.common.api.response.ErrorResponseDto;
import com.giant.common.api.response.ResponseDto;
import com.giant.common.api.type.ResponseCode;
import com.giant.common.api.type.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream().map(
                fe -> new ValidationError(
                        fe.getField(),
                        Optional.ofNullable(fe.getDefaultMessage()).filter(s -> !s.isBlank()).orElse("Invalid value")
                )).toList();

        log.warn("Validation error: {}", errors);

        return ResponseEntity.badRequest().body(ErrorResponseDto.from(ResponseCode.VALIDATION_ERROR, errors));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto> handleCustomException(CustomException ex) {
        log.error("Custom exception occurred: ", ex);
        return ResponseEntity.status(ex.getResponseCode().getStatus())
                .body(ErrorResponseDto.from(ex.getResponseCode()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseDto> handleDatabaseException(DataAccessException ex) {
        log.error("Database error occurred: ", ex);
        return ResponseEntity.internalServerError()
                .body(ErrorResponseDto.from(ResponseCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGeneralException(Exception ex) {
        log.error("Unhandled exception occurred: ", ex);
        return ResponseEntity.internalServerError()
                .body(ErrorResponseDto.from(ResponseCode.INTERNAL_SERVER_ERROR));
    }
}
