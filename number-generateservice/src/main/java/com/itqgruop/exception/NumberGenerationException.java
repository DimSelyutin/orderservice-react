package com.itqgruop.exception;

/**
 * Исключение, которое возникает при ошибках генерации номера.
 * <p>
 * Это исключение может указывать на различные проблемы, связанные с
 * процессом генерации номеров, такие как недопустимые параметры
 * или внутренние ошибки.
 * </p>
 */
public class NumberGenerationException extends RuntimeException {

    /**
     * Конструктор, создающий новое исключение с заданным сообщением.
     *
     * @param message Сообщение, описывающее причину исключения.
     */
    public NumberGenerationException(String message) {
        super(message);
    }

    /**
     * Конструктор, создающий новое исключение с заданным сообщением и причиной.
     *
     * @param message Сообщение, описывающее причину исключения.
     * @param cause   Причина возникновения исключения.
     */
    public NumberGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
