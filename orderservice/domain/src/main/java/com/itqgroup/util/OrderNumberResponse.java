package com.itqgroup.util;

/**
 * Ответ с информацией о номере заказа.
 */
public record OrderNumberResponse(
        String orderNumber,
        String createdAt,
        String updatedAt) {
}
