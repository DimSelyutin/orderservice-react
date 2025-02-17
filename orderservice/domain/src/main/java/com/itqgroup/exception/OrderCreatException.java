package com.itqgroup.exception;

/**
 * Исключение, которое возникает при ошибках создания заказа.
 * <p>
 * Это исключение может указывать на различные проблемы, связанные с
 * процессом создания заказа, такие как недопустимые параметры
 * или внутренние ошибки.
 * </p>
 */
public class OrderCreatException extends RuntimeException {

    /**
     * Конструктор, создающий новое исключение с заданным сообщением.
     *
     * @param message Сообщение, описывающее причину исключения.
     */
    public OrderCreatException(String message) {
        super(message);
    }

    /**
     * Конструктор, создающий новое исключение с заданным сообщением и причиной.
     *
     * @param message Сообщение, описывающее причину исключения.
     * @param cause   Причина возникновения исключения.
     */
    public OrderCreatException(String message, Throwable cause) {
        super(message, cause);
    }
}
