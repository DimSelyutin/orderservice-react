package com.itqgruop.service.impl;

import com.itqgruop.entity.OrderNumber;
import com.itqgruop.repository.MongoOrderNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderNumberServiceImplTest {

    @Mock
    private MongoOrderNumberRepository orderNumberRepository;

    @InjectMocks
    private OrderNumberServiceImpl orderNumberService;

    @Test
    void generateUniqueOrderNumber_ShouldReturnUniqueOrderNumber() {
        // Arrange
        OrderNumber mockOrderNumber = new OrderNumber();
        mockOrderNumber.setNumber("00001");
        when(orderNumberRepository.save(any(OrderNumber.class))).thenReturn(mockOrderNumber);

        // Act
        OrderNumber orderNumber = orderNumberService.generateUniqueOrderNumber();

        // Assert
        assertNotNull(orderNumber);
        assertNotNull(orderNumber.getNumber());
        assertTrue(orderNumber.getNumber().matches("\\d{5}")); // Проверка формата
        verify(orderNumberRepository, times(1)).save(any(OrderNumber.class)); // Убедитесь, что метод save был вызван
    }

    @Test
    void generateUniqueOrderNumber_ShouldGenerateDifferentNumbers() {
        // Arrange
        OrderNumber mockOrderNumber1 = new OrderNumber();
        mockOrderNumber1.setNumber("00001");
        OrderNumber mockOrderNumber2 = new OrderNumber();
        mockOrderNumber2.setNumber("00002");
        when(orderNumberRepository.save(any(OrderNumber.class)))
                .thenReturn(mockOrderNumber1)
                .thenReturn(mockOrderNumber2);

        // Act
        OrderNumber orderNumber1 = orderNumberService.generateUniqueOrderNumber();
        OrderNumber orderNumber2 = orderNumberService.generateUniqueOrderNumber();

        // Assert
        assertNotNull(orderNumber1);
        assertNotNull(orderNumber2);
        assertNotEquals(orderNumber1.getNumber(), orderNumber2.getNumber());
    }
}
