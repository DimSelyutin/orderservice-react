package com.itqgroup.service.impl;

import com.itqgroup.entity.Order;
import com.itqgroup.exception.OrderNotFoundException;
import com.itqgroup.repository.OrderRepository;
import com.itqgroup.service.client.OrderNumberClient;
import com.itqgroup.util.OrderNumberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private OrderRepository orderRepository;
    @Mock
    private OrderNumberClient orderNumberClient;
    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderServiceImpl(orderRepository, orderNumberClient);
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        var result = orderService.getAllOrders();

        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testCreateOrder() {
        Order order = new Order();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderNumberClient.fetchOrderNumber()).thenReturn(new OrderNumberResponse("00001", null, null));
        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrderById_Success() {
        Order order = new Order();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(1);

        assertNotNull(foundOrder);
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1));
        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderRepository).delete(1);

        orderService.deleteOrder(1);

        verify(orderRepository, times(1)).delete(1);
    }

    @Test
    void testGetOrdersByDate() {
        LocalDate date = LocalDate.now();
        Order order = new Order();
        when(orderRepository.findByDateAndTotalAmount(date, 100.0)).thenReturn(Arrays.asList(order));

        var result = orderService.getOrdersByDate(date, 100.0);

        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findByDateAndTotalAmount(date, 100.0);
    }

    @Test
    void testGetOrdersWithinDateRange() {
        LocalDate startDate = LocalDate.now().minusDays(5);
        LocalDate endDate = LocalDate.now();
        Order order = new Order();
        when(orderRepository.findOrdersExcludingProduct("product", startDate, endDate)).thenReturn(Arrays.asList(order));

        var result = orderService.getOrdersWithinDateRange("product", startDate, endDate);

        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findOrdersExcludingProduct("product", startDate, endDate);
    }
}
