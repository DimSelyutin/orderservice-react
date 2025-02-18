package com.itqgroup.repository.impl;

import com.itqgroup.entity.Order;
import com.itqgroup.entity.OrderDetail;
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
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class JdbcOrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcOrederDetailsRepository jdbcOrederDetailsRepository;

    /**
     * Получает список всех заказов из базы данных.
     *
     * @return список заказов.
     */
    @Override
    public List<Order> findAll() {
        log.debug("Retrieving all orders from the database.");
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    /**
     * Находит заказ по идентификатору.
     *
     * @param id идентификатор заказа.
     * @return Optional с найденным заказом или пустой Optional, если заказ не найден.
     */
    @Override
    public Optional<Order> findById(Integer id) {
        log.debug("Finding order with id: {}", id);
        String sql = "SELECT * FROM orders WHERE id = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new Object[] {id}, new OrderRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Order not found with id: {}", id);
            return Optional.empty();
        }
    }

    /**
     * Сохраняет новый заказ в базе данных.
     *
     * @param order заказ для сохранения.
     * @return сохраненный заказ с присвоенным идентификатором.
     */
    @Transactional
    @Override
    public Order save(Order order) {
        log.debug("Method save - start: {}", order);
        saveOrder(order);
        saveOrderDetails(order);
        log.debug("Order saved with recipient: {} and ID: {}", order.getRecipient(), order.getId());
        return order;
    }

    /**
     * Сохраняет основной заказ в базе данных и устанавливает идентификатор.
     *
     * @param order заказ для сохранения.
     */
    private void saveOrder(Order order) {
        String sql =
            "INSERT INTO orders (order_number, total_amount, order_date, recipient, delivery_address, payment_type, delivery_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getOrderNumber());
            ps.setDouble(2, order.getTotalAmount());
            ps.setDate(3, java.sql.Date.valueOf(order.getOrderDate()));
            ps.setString(4, order.getRecipient());
            ps.setString(5, order.getDeliveryAddress());
            ps.setString(6, order.getPaymentType());
            ps.setString(7, order.getDeliveryType());
            return ps;
        }, keyHolder);

        List<Map<String, Object>> keys = keyHolder.getKeyList();

        if (keys != null && !keys.isEmpty()) {
            if (keys.size() == 1) {
                Map<String, Object> keyMap = keys.get(0);
                order.setId(((Number) keyMap.get("id")).longValue());
            } else {
                log.warn("Multiple keys returned: {}", keys);
                throw new IllegalStateException("Multiple keys returned: " + keys);
            }
        } else {
            log.error("No key returned after insert.");
            throw new RuntimeException("No key returned after insert.");
        }
    }

    /**
     * Сохраняет детали заказа в базе данных.
     *
     * @param order заказ, для которого нужно сохранить детали.
     */
    private void saveOrderDetails(Order order) {
        for (OrderDetail detail : order.getOrderDetails()) {
            detail.setOrderId(order.getId());
            jdbcOrederDetailsRepository.createOrderDetail(detail);
        }
    }

    /**
     * Обновляет существующий заказ.
     *
     * @param id    идентификатор заказа для обновления.
     * @param order новые данные заказа.
     * @return обновленный заказ.
     */
    @Override
    public Order update(Integer id, Order order) {
        log.debug("Updating order with id: {}", id);
        String sql =
            "UPDATE orders SET order_date = ?, recipient = ?, delivery_address = ?, payment_type = ?, delivery_type = ? WHERE id             = ?";
        int updatedRows = jdbcTemplate.update(sql, order.getOrderDate(), order.getRecipient(),
            order.getDeliveryAddress(), order.getPaymentType(), order.getDeliveryType(), id);

        if (updatedRows == 0) {
            log.warn("Failed to update order: order not found with id: {}", id);
            throw new OrderNotFoundException("Order not found with id: " + id);
        }

        log.debug("Order updated with id: {}", id);
        return findById(id).orElseThrow(
            () -> new OrderNotFoundException("Order not found after update with id: " + id));
    }

    /**
     * Удаляет заказ по идентификатору.
     *
     * @param id идентификатор заказа для удаления.
     */
    @Override
    public void delete(Integer id) {
        log.debug("Deleting order with id: {}", id);
        String sql = "DELETE FROM orders WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sql, id);

        if (deletedRows == 0) {
            log.warn("Failed to delete order: order not found with id: {}", id);
            throw new OrderNotFoundException("Order not found with id: " + id);
        }

        log.debug("Order deleted with id: {}", id);
    }

    /**
     * Находит заказы по дате и общей сумме.
     *
     * @param date        дата заказа.
     * @param totalAmount общая сумма заказа.
     * @return список найденных заказов.
     */
    @Override
    public List<Order> findByDateAndTotalAmount(LocalDate date, double totalAmount) {
        log.debug("Finding orders with date: {} and total amount: {}", date, totalAmount);
        String sql = "SELECT * FROM orders WHERE order_date >= ? AND total_amount >= ?";
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        return jdbcTemplate.query(sql, new Object[] {sqlDate, totalAmount}, new OrderRowMapper());
    }

    /**
     * Находит заказы, исключая определенный продукт, в заданном диапазоне дат.
     *
     * @param productName название продукта, который нужно исключить.
     * @param startDate   начальная дата диапазона.
     * @param endDate     конечная дата диапазона.
     * @return список найденных заказов.
     */
    @Override
    public List<Order> findOrdersExcludingProduct(String productName, LocalDate startDate, LocalDate endDate) {
        log.debug("Finding orders excluding product: {} between dates: {} and {}", productName, startDate, endDate);
        String sql = "SELECT * FROM orders o " +
            "WHERE NOT EXISTS (SELECT 1 FROM order_products op WHERE op.order_id = o.id AND op.product_name = ?) " +
            "AND o.order_date BETWEEN ? AND ?";
        java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
        java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);
        return jdbcTemplate.query(sql, new Object[] {productName, sqlStartDate, sqlEndDate}, new OrderRowMapper());
    }

    /**
     * Внутренний класс для маппинга результата запроса в объект Order.
     */
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
