package com.itqgroup.advice;

import com.itqgroup.exception.OrderNotFoundException;
import com.itqgroup.openapi.model.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
     * Обработка исключения OrderNotFoundException.
     *
     * @param ex исключение, которое будет обрабатываться.
     * @return ResponseEntity с объектом ErrorDto и статусом 404 (Not Found).
     */
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handleOrderNotFoundException(OrderNotFoundException ex) {
        log.error("Заказ не найден: {}", ex.getMessage());
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    /**
     * Обработка всех других исключений.
     *
     * @param ex исключение, которое будет обрабатываться.
     * @return ResponseEntity с объектом ErrorDto и статусом 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex) {
        log.error("Произошла непредвиденная ошибка: {}", ex.getMessage(), ex);
        ErrorDto errorDto = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Произошла непредвиденная ошибка. Пожалуйста, попробуйте позже.",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }
}
