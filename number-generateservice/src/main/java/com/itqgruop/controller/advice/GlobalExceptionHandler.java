package com.itqgruop.controller.advice;

import com.itqgroup.openapi.model.ErrorDto;
import com.itqgruop.exception.NumberGenerationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Глобальный обработчик исключений для контроллеров.
 * Перехватывает и обрабатывает исключения, возникающие в контроллерах.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработка исключения NumberGenerationException.
     *
     * @param ex исключение, которое будет обрабатываться.
     * @return ResponseEntity с сообщением и статусом 400 (Bad Request).
     */
    @ExceptionHandler(NumberGenerationException.class)
    public ResponseEntity<ErrorDto> handleNumberGenerationException(NumberGenerationException ex) {
        log.error("Ошибка генерации номера: {}", ex.getMessage());
        ErrorDto error = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.ordinal(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Обработка всех других исключений.
     *
     * @param ex исключение, которое будет обрабатываться.
     * @return ResponseEntity с сообщением и статусом 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        log.error("Произошла непредвиденная ошибка: {}", ex.getMessage(), ex);
        ErrorDto error = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.ordinal(), "Произошла непредвиденная ошибка. Пожалуйста, попробуйте позже.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
