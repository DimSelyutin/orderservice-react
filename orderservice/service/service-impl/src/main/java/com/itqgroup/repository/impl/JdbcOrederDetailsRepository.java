package com.itqgroup.repository.impl;

import com.itqgroup.entity.OrderDetail;
import com.itqgroup.repository.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JdbcOrederDetailsRepository implements OrderDetailsRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * @param orderDetail
     */
    @Override
    public void createOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO order_details (product_article, product_name, quantity, unit_price, order_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderDetail.getProductArticle(), orderDetail.getProductName(), orderDetail.getQuantity(), orderDetail.getUnitPrice(), orderDetail.getOrderId());
    }

    /**
     * @param orderId
     */
    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_details WHERE order_id = ?";
        return jdbcTemplate.query(sql, new Object[]{orderId}, new OrderDetailRowMapper());
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
