package com.itqgroup.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

/**
 * Сущность деталей заказа.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private Long id;
    private Long productArticle;
    private String productName;
    private int quantity;
    private Double unitPrice;
    private Long orderId;
}
