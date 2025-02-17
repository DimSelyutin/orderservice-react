package com.itqgroup.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.util.List;
import java.time.LocalDate;

/**
 * Класс сущность для заказа.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String orderNumber;
    private Double totalAmount;
    private LocalDate orderDate;
    private String recipient;
    private String deliveryAddress;
    private String paymentType;
    private String deliveryType;
    private List<OrderDetail> orderDetails;
}
