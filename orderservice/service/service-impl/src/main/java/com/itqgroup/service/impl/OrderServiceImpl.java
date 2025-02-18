package com.itqgroup.service.impl;


import com.itqgroup.entity.Order;
import com.itqgroup.exception.OrderNotFoundException;
import com.itqgroup.repository.OrderRepository;
import com.itqgroup.service.OrderService;
import com.itqgroup.service.client.OrderNumberClient;
import com.itqgroup.util.OrderNumberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderNumberClient orderNumberClientl;

    /**
     * Получает список всех заказов.
     *
     * @return список объектов Order, представляющих все заказы.
     */
    @Transactional(readOnly = true)
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Создает новый заказ.
     *
     * @param order объект заказа, который нужно создать.
     * @return объект Order, представляющий созданный заказ.
     */
    @Override
    public Order createOrder(Order order) {
        log.debug("createOrder - start, order: {}", order);
        OrderNumberResponse orderNumberResponse = orderNumberClientl.fetchOrderNumber();
        log.debug("createOrder - orderNumberResponse: {}", orderNumberResponse);

        order.setOrderNumber(orderNumberResponse.orderNumber());
        log.debug("createOrder - changed order: {}", order);

        return orderRepository.save(order);
    }

    /**
     * Получает заказ по идентификатору.
     *
     * @param id идентификатор заказа.
     * @return объект Order, представляющий найденный заказ.
     */
    @Transactional(readOnly = true)
    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
    }

    /**
     * Удаляет заказ по идентификатору.
     *
     * @param id идентификатор заказа, который нужно удалить.
     * @return null, если удаление прошло успешно.
     */
    @Override
    public void deleteOrder(Integer id) {
        orderRepository.delete(id);
    }

    /**
     * Получает список заказов по указанной дате.
     *
     * @param date дата, по которой нужно получить заказы.
     * @return список объектов Order, представляющих заказы на данную дату.
     */
    @Transactional(readOnly = true)
    @Override
    public List<Order> getOrdersByDate(LocalDate date, double totalAmount) {
        return orderRepository.findByDateAndTotalAmount(date, totalAmount);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getOrdersWithinDateRange(String productName, LocalDate startDate, LocalDate endDate) {

        return orderRepository.findOrdersExcludingProduct(productName, startDate, endDate);
    }
}
