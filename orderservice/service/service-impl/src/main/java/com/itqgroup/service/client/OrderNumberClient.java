package com.itqgroup.service.client;

import com.itqgroup.exception.OrderCreatException;
import com.itqgroup.util.OrderNumberResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Setter
@Getter
@RequiredArgsConstructor
@Component
public class OrderNumberClient {

    private final RestTemplate restTemplate;

    @Value("${order.number.api.url}")
    private String orderNumberApiUrl;

    public OrderNumberResponse fetchOrderNumber() {
        log.debug("fetchOrderNumber - Запрос к API: {}", orderNumberApiUrl);
        try {
            return restTemplate.getForObject(orderNumberApiUrl.trim(), OrderNumberResponse.class);
        } catch (RestClientException e) {
            log.debug("Не удалось получить номер заказа: {}", e.getMessage());
            throw new OrderCreatException("Ошибка при получении номера заказа");
        }
    }
}
