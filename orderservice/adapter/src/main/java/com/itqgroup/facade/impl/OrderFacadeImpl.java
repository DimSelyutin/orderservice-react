package com.itqgroup.facade.impl;

import com.itqgroup.entity.Order;
import com.itqgroup.facade.OrderFacade;
import com.itqgroup.mapper.OrderMapper;
import com.itqgroup.openapi.model.OrderDto;
import com.itqgroup.openapi.model.OrderWithDetailDto;
import com.itqgroup.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса OrderFacade.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    /**
     * Получает список всех заказов.
     *
     * @return список DTO заказов.
     */
    @Override
    public List<OrderDto> getAllOrders() {
        log.debug("getAllOrders - start");

        // Получаем список сущностей
        List<Order> orders = orderService.getAllOrders();

        // Преобразуем список сущностей в DTO
        List<OrderDto> ordersDto = orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());

        log.debug("getAllOrders - end, total orders = {}", ordersDto.size());
        return ordersDto;
    }

    /**
     * Создает новый заказ.
     *
     * @param orderDto объект заказа для создания.
     * @return созданный объект заказа.
     */
    @Override
    public OrderWithDetailDto createOrder(OrderWithDetailDto orderDto) {
        log.debug("createOrder - start, orderDto = {}", orderDto);
        Order order = orderMapper.toOrder(orderDto);
        Order createdOrder = orderService.createOrder(order);
        OrderWithDetailDto createdOrderDto = orderMapper.toOrderWithDetailDto(createdOrder);
        log.debug("createOrder - end, createdOrderDto = {}", createdOrderDto);
        return createdOrderDto;
    }

    /**
     * Получает заказ по идентификатору.
     *
     * @param id идентификатор заказа.
     * @return объект заказа с указанным идентификатором.
     */
    @Override
    public OrderWithDetailDto getOrderById(Integer id) {
        log.debug("getOrderById - start, id = {}", id);
        OrderWithDetailDto order = orderMapper.toOrderWithDetailDto(orderService.getOrderById(id));
        log.debug("getOrderById - end, order = {}", order);
        return order;
    }

    /**
     * Удаляет заказ по идентификатору.
     *
     * @param id идентификатор заказа для удаления.
     */
    @Override
    public void deleteOrder(Integer id) {
        log.debug("deleteOrder - start, id = {}", id);
        orderService.deleteOrder(id);
        log.debug("deleteOrder - end");
    }

    /**
     * Получает список заказов по заданной дате.
     *
     * @param date дата, по которой нужно получить заказы.
     * @return список DTO заказов на эту дату.
     */
    @Override
    public List<OrderWithDetailDto> getOrdersByDate(LocalDate date, Float total) {
        log.debug("getOrdersByDate - start, date = {}", date);

        List<Order> orders = orderService.getOrdersByDate(date, total);


        List<OrderWithDetailDto> ordersDto = orders.stream()
                .map(orderMapper::toOrderWithDetailDto)
                .collect(Collectors.toList());

        log.debug("getOrdersByDate - end, total orders = {}", ordersDto.size());
        return ordersDto;
    }

    /**
     * Получает список заказов в пределах указанного диапазона дат.
     *
     * @param startDate начальная дата диапазона.
     * @param endDate   конечная дата диапазона.
     * @return список DTO заказов в указанном диапазоне.
     */
    @Override
    public List<OrderWithDetailDto> getOrdersWithinDateRange(String productName, LocalDate startDate, LocalDate endDate) {
        log.debug("getOrdersWithinDateRange - start, startDate = {}, endDate = {}", startDate, endDate);

        List<Order> orders = orderService.getOrdersWithinDateRange(productName, startDate, endDate);


        List<OrderWithDetailDto> ordersDto = orders.stream()
                .map(orderMapper::toOrderWithDetailDto)
                .collect(Collectors.toList());

        log.debug("getOrdersWithinDateRange - end, total orders = {}", ordersDto.size());
        return ordersDto;
    }
}
