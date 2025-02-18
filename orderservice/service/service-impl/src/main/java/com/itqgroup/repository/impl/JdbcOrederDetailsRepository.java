package com.itqgroup.repository.impl;

import com.itqgroup.entity.OrderDetail;
import com.itqgroup.repository.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class JdbcOrederDetailsRepository implements OrderDetailsRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Создает детали заказа в базе данных.
     *
     * @param orderDetail детали заказа, которые необходимо сохранить.
     */
    @Override
    public void createOrderDetail(OrderDetail orderDetail) {
        log.debug("Creating order detail: {}", orderDetail);
        String sql =
            "INSERT INTO order_details (product_article, product_name, quantity, unit_price, order_id) VALUES (?, ?, ?, ?, ?)";

        int rowsAffected = jdbcTemplate.update(sql, orderDetail.getProductArticle(), orderDetail.getProductName(),
            orderDetail.getQuantity(), orderDetail.getUnitPrice(), orderDetail.getOrderId());

        log.info("Order detail created successfully. Rows affected: {}", rowsAffected);
    }

    /**
     * Получает список деталей заказа по идентификатору заказа.
     *
     * @param orderId идентификатор заказа, для которого необходимо получить детали.
     * @return список деталей заказа.
     */
    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        log.debug("Retrieving order details for order ID: {}", orderId);
        String sql = "SELECT * FROM order_details WHERE order_id = ?";

        List<OrderDetail> orderDetails = jdbcTemplate.query(sql, new Object[] {orderId}, new OrderDetailRowMapper());
        log.info("Retrieved {} order details for order ID: {}", orderDetails.size(), orderId);

        return orderDetails;
    }

    private static class OrderDetailRowMapper implements RowMapper<OrderDetail> {

        @Override
        public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(rs.getLong("id"));
            orderDetail.setProductArticle(rs.getLong("product_article"));
            orderDetail.setProductName(rs.getString("product_name"));
            orderDetail.setQuantity(rs.getInt("quantity"));
            orderDetail.setUnitPrice(rs.getDouble("unit_price"));
            orderDetail.setOrderId(rs.getLong("order_id"));
            return orderDetail;
        }
    }
}
