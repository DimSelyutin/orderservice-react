package com.itqgruop.repository;

import com.itqgruop.entity.OrderNumber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс репозитория для управления сущностями заказов.
 * Предоставляет методы для взаимодействия с базой данных.
 */
@Repository
public interface MongoOrderNumberRepository extends MongoRepository<OrderNumber, String> {
    Optional<OrderNumber> findByNumber(String orderNumber);
}

