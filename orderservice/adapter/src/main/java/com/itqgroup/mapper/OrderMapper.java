package com.itqgroup.mapper;

import com.itqgroup.entity.Order;
import com.itqgroup.entity.OrderDetail;
import com.itqgroup.openapi.model.OrderDetailDto;
import com.itqgroup.openapi.model.OrderDto;
import com.itqgroup.openapi.model.OrderWithDetailDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper для преобразования между сущностью {@link Order} и DTO {@link OrderDto} и {@link OrderWithDetailDto}.
 */
@Mapper(componentModel = "SPRING", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderMapper {

    /**
     * Преобразует сущность заказа {@link Order} в объект DTO заказа {@link OrderDto}.
     *
     * @param order объект сущности заказа, который нужно преобразовать.
     * @return объект DTO заказа, созданный на основе переданного заказа.
     */
    OrderDto toDto(Order order);

    /**
     * Преобразует сущность заказа {@link Order} в объект DTO заказа с деталями {@link OrderWithDetailDto}.
     *
     * @param order объект сущности заказа, который нужно преобразовать.
     * @return объект DTO заказа с деталями, созданный на основе переданного заказа.
     */
    @Mapping(target = "orderDetails", source = "orderDetails")
    OrderWithDetailDto toOrderWithDetailDto(Order order);

    /**
     * Преобразует объект DTO заказа с деталями {@link OrderWithDetailDto} в сущность заказа {@link Order}.
     *
     * @param orderWithDetailDto объект DTO заказа с деталями, который нужно преобразовать.
     * @return объект сущности заказа, созданный на основе переданного DTO.
     */
    @Mapping(target = "orderDetails", source = "orderDetails")
    Order toOrder(OrderWithDetailDto orderWithDetailDto);
}
