package com.itqgroup.repository;

import com.itqgroup.entity.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAll();

    Optional<Order> findById(Integer id);

    Order save(Order order);

    Order update(Integer id, Order order);

    void delete(Integer id);

    List<Order> findOrdersExcludingProduct(String productName, LocalDate startDate, LocalDate endDate);

    List<Order> findByDateAndTotalAmount(LocalDate date, double totalAmount);
}

