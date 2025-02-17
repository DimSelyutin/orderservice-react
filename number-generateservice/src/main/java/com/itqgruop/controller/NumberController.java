package com.itqgruop.controller;

import com.itqgroup.openapi.api.NumbersApi;
import com.itqgroup.openapi.model.GeneratedNumberDTO;
import com.itqgruop.facade.OrderNumberFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для работы с номерами заказов.
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/numbers")
@RestController
public class NumberController implements NumbersApi {

    private final OrderNumberFacade orderNumberFacade;

    @Override
    @GetMapping("")
    public ResponseEntity<GeneratedNumberDTO> getOrderNumber() {
        log.info("Запрос на генерацию уникального номера заказа поступил.");
        GeneratedNumberDTO number = orderNumberFacade.generateUniqueOrderNumber();
        log.info("Сгенерирован уникальный номер заказа: {}", number.getOrderNumber());
        return ResponseEntity.ok(number);
    }
}
