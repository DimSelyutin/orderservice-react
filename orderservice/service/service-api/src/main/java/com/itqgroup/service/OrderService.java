package com.itqgroup.service;

import com.itqgroup.entity.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс сервиса заказов, обеспечивающий CRUD операции для управления заказами.
 */
@Service
public interface OrderService {

    /**
     * Получает список всех заказов.
     *
     * @return список объектов Order, представляющих все заказы.
     */
    List<Order> getAllOrders();

    /**
     * Создает новый заказ.
     *
     * @param order объект заказа, который нужно создать.
     * @return объект Order, представляющий созданный заказ.
     */
    Order createOrder(Order order);

    /**
     * Получает заказ по идентификатору.
     *
     * @param id идентификатор заказа.
     * @return объект Order, представляющий найденный заказ.
     */
    Order getOrderById(Integer id);

    /**
     * Удаляет заказ по идентификатору.
     *
     * @param id идентификатор заказа, который нужно удалить.
     * @return null, если удаление прошло успешно.
     */
    void deleteOrder(Integer id);

    /**
     * Получает список заказов по указанной дате.
     *
     * @param date дата, по которой нужно получить заказы.
     * @return список объектов Order, представляющих заказы на данную дату.
     */
    List<Order> getOrdersByDate(LocalDate date, double totalAmount);

    /**
     * Получает список заказов в пределах указанного диапазона дат.
     *
     * @param startDate начальная дата диапазона.
     * @param endDate   конечная дата диапазона.
     * @return список объектов Order, представляющих заказы в указаом диапазоне.
     */
    List<Order> getOrdersWithinDateRange(String productName, LocalDate startDate, LocalDate endDate);
}
