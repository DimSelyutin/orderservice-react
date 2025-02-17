package com.itqgruop.service;

import com.itqgruop.entity.OrderNumber;
import org.springframework.stereotype.Service;


/**
 * Интерфейс сервиса для управления номерами заказов.
 * Предоставляет методы для генерации уникальных номеров заказов.
 */
@Service
public interface OrderNumberService {

    /**
     * Генерирует уникальный номер заказа.
     * <p>
     * Этот метод создает случайный номер заказа, проверяет его на уникальность
     * и сохраняет в базе данных. Если номер уже существует, генерируется новый.
     *
     * @return объект OrderNumber с уникальным номером заказа и текущими временными метками
     */
    OrderNumber generateUniqueOrderNumber();
}
