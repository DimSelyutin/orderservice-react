package com.itqgroup.service.client;

import com.itqgroup.exception.OrderCreatException;
import com.itqgroup.util.OrderNumberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderNumberClientTest {

    private OrderNumberClient orderNumberClient;
    private RestTemplate restTemplate;

    @Value("${order.number.api.url}")
    private String orderNumberApiUrl = "http://example.com/order/number";

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        orderNumberClient = new OrderNumberClient(restTemplate);
        orderNumberClient.setOrderNumberApiUrl(orderNumberApiUrl);
    }

    @Test
    void fetchOrderNumber_Success() {
        // Arrange
        OrderNumberResponse expectedResponse = new OrderNumberResponse("00001",null,null);

        when(restTemplate.getForObject(orderNumberApiUrl, OrderNumberResponse.class))
                .thenReturn(expectedResponse);

        // Act
        OrderNumberResponse actualResponse = orderNumberClient.fetchOrderNumber();

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate).getForObject(orderNumberApiUrl, OrderNumberResponse.class);
    }

    @Test
    void fetchOrderNumber_RestClientException() {
        // Arrange
        when(restTemplate.getForObject(orderNumberApiUrl, OrderNumberResponse.class))
                .thenThrow(new RestClientException("Ошибка подключения"));

        // Act & Assert
        OrderCreatException thrown = assertThrows(OrderCreatException.class, () -> {
            orderNumberClient.fetchOrderNumber();
        });

        assertEquals("Ошибка при получении номера заказа", thrown.getMessage());
        assertNotNull(thrown.getCause());
        assertEquals(RestClientException.class, thrown.getCause().getClass());
        verify(restTemplate).getForObject(orderNumberApiUrl, OrderNumberResponse.class);
    }
}
