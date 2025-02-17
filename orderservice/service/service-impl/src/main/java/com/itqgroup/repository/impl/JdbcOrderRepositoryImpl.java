package com.itqgroup.repository.impl;

import com.itqgroup.entity.Order;
import com.itqgroup.exception.OrderNotFoundException;
import com.itqgroup.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class JdbcOrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcOrederDetailsRepository jdbcOrederDetailsRepository;

    @Override
    public List<Order> findAll() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    @Override
    public Optional<Order> findById(Integer id) {
        String sql = "SELECT * FROM orders WHERE id = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{id}, new OrderRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Order not found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public Order save(Order order) {
        String sql = "INSERT INTO orders (order_date, recipient, delivery_address, payment_type, delivery_type) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); // Для хранения сгенерированного ключа

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // Указываем, что хотим получать ключи
            ps.setDate(1, java.sql.Date.valueOf(order.getOrderDate())); // Преобразуем LocalDate в java.sql.Date
            ps.setString(2, order.getRecipient());
            ps.setString(3, order.getDeliveryAddress());
            ps.setString(4, order.getPaymentType());
            ps.setString(5, order.getDeliveryType());
            return ps;
        }, keyHolder);

        // Получаем сгенерированный ключ и присваиваем его заказу
        order.setId(keyHolder.getKey().longValue());

        log.info("Order saved with recipient: {} and ID: {}", order.getRecipient(), order.getId());

        return order;
    }


    @Override
    public Order update(Integer id, Order order) {
        String sql = "UPDATE orders SET order_date = ?, recipient = ?, delivery_address = ?, payment_type = ?, delivery_type = ? WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql, order.getOrderDate(), order.getRecipient(),
                order.getDeliveryAddress(), order.getPaymentType(), order.getDeliveryType(), id);

        if (updatedRows == 0) {
            log.warn("Failed to update order: order not found with id: {}", id);
            throw new OrderNotFoundException("Order not found with id: " + id);
        }

        log.info("Order updated with id: {}", id);

        return findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found after update with id: " + id));
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sql, id);

        if (deletedRows == 0) {
            log.warn("Failed to delete order: order not found with id: {}", id);
            throw new OrderNotFoundException("Order not found with id: " + id);
        }

        log.info("Order deleted with id: {}", id);
    }

    @Override
    public List<Order> findByDateAndTotalAmount(LocalDate date, double totalAmount) {
        String sql = "SELECT * FROM orders WHERE order_date >= ? AND total_amount >= ?";
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        return jdbcTemplate.query(sql, new Object[]{sqlDate, totalAmount}, new OrderRowMapper());
    }

    @Override
    public List<Order> findOrdersExcludingProduct(String productName, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM orders o " +
                "WHERE NOT EXISTS (SELECT 1 FROM order_products op WHERE op.order_id = o.id AND op.product_name = ?) " +
                "AND o.order_date BETWEEN ? AND ?";
        java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
        java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);
        return jdbcTemplate.query(sql, new Object[]{productName, sqlStartDate, sqlEndDate}, new OrderRowMapper());
    }


    private static class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getLong("id"));
            order.setOrderDate(rs.getDate("order_date").toLocalDate());
            order.setRecipient(rs.getString("recipient"));
            order.setDeliveryAddress(rs.getString("delivery_address"));
            order.setPaymentType(rs.getString("payment_type"));
            order.setDeliveryType(rs.getString("delivery_type"));
            return order;
        }
    }
}
