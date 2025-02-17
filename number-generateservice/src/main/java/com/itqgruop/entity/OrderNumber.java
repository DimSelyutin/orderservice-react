package com.itqgruop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Класс OrderNumber представляет собой сущность заказа с уникальным номером.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "generatedNumbers")
public class OrderNumber {

    private String number;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
