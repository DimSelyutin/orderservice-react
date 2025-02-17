package com.itqgroup.controller;

import com.itqgroup.facade.OrderFacade;
import com.itqgroup.openapi.model.OrderDto;
import com.itqgroup.openapi.model.OrderWithDetailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderFacade orderFacade;

    @InjectMocks
    private OrderController orderController;

    private OrderWithDetailDto order1 = new OrderWithDetailDto();
    private OrderDto orderDto = new OrderDto();
    private OrderDto orderDto2 = new OrderDto();

    private OrderWithDetailDto order2 = new OrderWithDetailDto();
    private List<OrderWithDetailDto> orders;
    private List<OrderDto> ordersDto;

    @BeforeEach
    void setUp() {
        orders = List.of(order1, order2);
        ordersDto = List.of(orderDto, orderDto2);
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        when(orderFacade.getAllOrders()).thenReturn(ordersDto);

        // Act
        ResponseEntity<List<OrderDto>> responseEntity = orderController.getAllOrders();
        List<OrderDto> result = responseEntity.getBody();

        // Assert
        assertEquals(ordersDto, result);
        verify(orderFacade, times(1)).getAllOrders();
    }


    @Test
    void testGetOrderById() {
        // Arrange
        Integer orderId = 1;
        when(orderFacade.getOrderById(orderId)).thenReturn(order1);

        // Act
        ResponseEntity<OrderWithDetailDto> responseEntity = orderController.getOrderById(orderId);
        OrderWithDetailDto result = responseEntity.getBody(); // Извлекаем тело ответа

        // Assert
        assertEquals(order1, result);
        verify(orderFacade, times(1)).getOrderById(orderId);
    }

    @Test
    void testCreateOrder() {
        // Arrange
        when(orderFacade.createOrder(any(OrderWithDetailDto.class))).thenReturn(order1);

        // Act
        ResponseEntity<OrderWithDetailDto> responseEntity = orderController.createOrder(order1);
        OrderWithDetailDto result = responseEntity.getBody();

        // Assert
        assertEquals(order1, result);
        verify(orderFacade, times(1)).createOrder(any(OrderWithDetailDto.class));
    }

    @Test
    void testGetOrdersExcludingProduct() {
        // Arrange
        String productName = "Test Product";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        when(orderFacade.getOrdersWithinDateRange(productName, startDate, endDate)).thenReturn(orders);

        // Act
        ResponseEntity<List<OrderWithDetailDto>> responseEntity = orderController.getOrdersExcludingProduct(productName, startDate, endDate);
        List<OrderWithDetailDto> result = responseEntity.getBody();

        // Assert
        assertEquals(orders, result);
        verify(orderFacade, times(1)).getOrdersWithinDateRange(productName, startDate, endDate);
    }

    @Test
    void testGetOrdersByDateAndTotal() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 1, 1);
        Float total = 100.0f;
        when(orderFacade.getOrdersByDate(date, total)).thenReturn(orders);

        // Act
        ResponseEntity<List<OrderWithDetailDto>> responseEntity = orderController.getOrdersByDateAndTotal(date, total);
        List<OrderWithDetailDto> result = responseEntity.getBody();

        // Assert
        assertEquals(orders, result);
        verify(orderFacade, times(1)).getOrdersByDate(date, total);
    }
}
