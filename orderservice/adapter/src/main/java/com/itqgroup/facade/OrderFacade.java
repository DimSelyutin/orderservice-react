package com.itqgroup.facade;

import com.itqgroup.openapi.model.OrderDto;
import com.itqgroup.openapi.model.OrderWithDetailDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс для фасада заказов, обеспечивающий удобный доступ к операциям с заказами.
 */
public interface OrderFacade {

    /**
     * Получает список всех заказов.
     *
     * @return список DTO заказов.
     */
    List<OrderDto> getAllOrders();

    /**
     * Создает новый заказ.
     *
     * @param orderDto объект заказа для создания.
     * @return созданный объект заказа.
     */
    OrderWithDetailDto createOrder(OrderWithDetailDto orderDto);

    /**
     * Получает заказ по идентификатору.
     *
     * @param id идентификатор заказа.
     * @return объект заказа с указанным идентификатором.
     */
    OrderWithDetailDto getOrderById(Integer id);

    /**
     * Удаляет заказ по идентификатору.
     *
     * @param id идентификатор заказа для удаления.
     */
    void deleteOrder(Integer id);

    /**
     * Получает список заказов в пределах указанного диапазона дат.
     *
     * @param startDate начальная дата диапазона.
     * @param endDate   конечная дата диапазона.
     * @return список DTO заказов в указанном диапазоне.
     */
    List<OrderWithDetailDto> getOrdersWithinDateRange(String productName, LocalDate startDate, LocalDate endDate);

    /**
     * Получает список заказов по заданной дате.
     *
     * @param date дата, по которой нужно получить заказы.
     * @return список DTO заказов на эту дату.
     */
    List<OrderWithDetailDto> getOrdersByDate(LocalDate date, Float total);
}

