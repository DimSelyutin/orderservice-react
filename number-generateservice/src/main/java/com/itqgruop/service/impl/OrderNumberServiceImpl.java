package com.itqgruop.service.impl;

import com.itqgruop.entity.OrderNumber;
import com.itqgruop.exception.NumberGenerationException;
import com.itqgruop.repository.MongoOrderNumberRepository;
import com.itqgruop.service.OrderNumberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Реализация сервиса для управления номерами заказов.
 * Предоставляет методы для генерации уникальных номеров заказов.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderNumberServiceImpl implements OrderNumberService {

    private final MongoOrderNumberRepository mongoOrderNumberRepository;
    private final Random random = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderNumber generateUniqueOrderNumber() {
        log.info("Method generateUniqueOrderNumber - start");
        String orderNumber;
        boolean isUnique;

        do {
            orderNumber = String.format("%05d", random.nextInt(100000));
            log.info("Method generateUniqueOrderNumber - generatedNumber: {}", orderNumber);

            try {
                isUnique = mongoOrderNumberRepository.findByNumber(orderNumber).isEmpty();
            } catch (Exception e) {
                log.error("Ошибка при обращении к MongoDB: {}", e.getMessage(), e);
                throw new NumberGenerationException(e.getMessage());
            }
        } while (!isUnique);

        OrderNumber newOrderNumber = new OrderNumber(orderNumber, LocalDateTime.now(), LocalDateTime.now());
        return mongoOrderNumberRepository.save(newOrderNumber);

    }

}
