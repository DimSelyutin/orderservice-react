package com.itqgroup.controller;

import com.itqgroup.facade.OrderFacade;
import com.itqgroup.openapi.api.OrdersApi;
import com.itqgroup.openapi.model.OrderDto;
import com.itqgroup.openapi.model.OrderWithDetailDto;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер для работы с заказами.
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController implements OrdersApi {

    private final OrderFacade orderFacade;

    /**
     * Получение списка всех заказов.
     *
     * @return список заказов
     */
    @Override
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        log.debug("GET-request, getAllOrders - start");
        List<OrderDto> orders = orderFacade.getAllOrders();
        log.debug("GET-request, getAllOrders - end, response size = {}", orders.size());
        return ResponseEntity.ok(orders);
    }

    /**
     * Получение заказа по ID.
     *
     * @param id идентификатор заказа
     * @return заказ
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrderWithDetailDto> getOrderById(@PathVariable Integer id) {
        log.debug("GET-request, getOrderById - start, order's id = {}", id);
        OrderWithDetailDto order = orderFacade.getOrderById(id);
        log.debug("GET-request, getOrderById - end, response = {}", order);
        return ResponseEntity.ok(order);
    }


    /**
     * Создание нового заказа.
     *
     * @param orderWithDetailDto новый заказ
     * @return созданный заказ
     */
    @Override
    @PostMapping
    public ResponseEntity<OrderWithDetailDto> createOrder(
        @Parameter(name = "OrderWithDetailDto", description = "", required = true)
        @RequestBody OrderWithDetailDto orderWithDetailDto
    ) {
        log.debug("POST-request, createOrder - start, order = {}", orderWithDetailDto);
        OrderWithDetailDto createdOrder = orderFacade.createOrder(orderWithDetailDto);
        log.debug("POST-request, createOrder - end, created order = {}", createdOrder);
        return ResponseEntity.status(201).body(createdOrder);
    }

    /**
     * Получение заказов по заданной дате.
     *
     * @param date дата, по которой нужно получить заказы
     * @return список заказов на эту дату
     */
    @Override
    @GetMapping("/date/{date}")
    public ResponseEntity<List<OrderWithDetailDto>> getOrdersByDateAndTotal(
            LocalDate date, @RequestParam(value = "total", required = true) Float total) {
        log.debug("GET-request, getOrdersByDate - start, date = {}", date);
        List<OrderWithDetailDto> orders = orderFacade.getOrdersByDate(date, total);
        log.debug("GET-request, getOrdersByDate - end, response size = {}", orders.size());
        return ResponseEntity.ok(orders);
    }

    /**
     * Получение заказов в заданном диапазоне дат.
     *
     * @param startDate начальная дата диапазона
     * @param endDate   конечная дата диапазона
     * @return список заказов в рамках указанного диапазона
     */
    @Override
    @GetMapping("/date-range")
    public ResponseEntity<List<OrderWithDetailDto>> getOrdersExcludingProduct(@RequestParam(value = "productName", required = true) String productName,
                                                                              @RequestParam(value = "startDate", required = true) LocalDate startDate,
                                                                              @RequestParam(value = "endDate", required = true) LocalDate endDate
    ) {
        log.debug("GET-request, getOrdersWithinDateRange - start, startDate = {}, endDate = {}", startDate, endDate);
        List<OrderWithDetailDto> orders = orderFacade.getOrdersWithinDateRange(productName, startDate, endDate);
        log.debug("GET-request, getOrdersWithinDateRange - end, response size = {}", orders.size());
        return ResponseEntity.ok(orders);
    }
}

