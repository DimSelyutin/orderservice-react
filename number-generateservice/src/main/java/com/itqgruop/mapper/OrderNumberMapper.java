package com.itqgruop.mapper;

import com.itqgroup.openapi.model.GeneratedNumberDTO;
import com.itqgruop.entity.OrderNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * Интерфейс OrderNumberMapper предназначен для преобразования объектов
 * между сущностями базы данных и объектами передачи данных (DTO) для
 * заказов.
 */
@Mapper(componentModel = "spring",
        injectionStrategy = CONSTRUCTOR,
        unmappedTargetPolicy = IGNORE)
public interface OrderNumberMapper {

    /**
     * Преобразует объект OrderNumber в объект OrderNumberDTO.
     *
     * @param orderNumber объект, который необходимо преобразовать
     * @return преобразованный объект OrderNumberDTO
     */
    @Mapping(target = "orderNumber", source = "orderNumber.number")
    GeneratedNumberDTO toDto(OrderNumber orderNumber);

    /**
     * Преобразует объект OrderNumberDTO в объект OrderNumber.
     *
     * @param orderNumberDTO объект, который необходимо преобразовать
     * @return преобразованный объект OrderNumber
     */
    OrderNumber toEntity(GeneratedNumberDTO orderNumberDTO);

    /**
     * Преобразует список объектов OrderNumber в список объектов OrderNumberDTO.
     *
     * @param orderNumbers список объектов, которые необходимо преобразовать
     * @return список преобразованных объектов OrderNumberDTO
     */
    List<GeneratedNumberDTO> toDtoList(List<OrderNumber> orderNumbers);

    /**
     * Преобразует список объектов OrderNumberDTO в список объектов OrderNumber.
     *
     * @param orderNumberDTOs список объектов, которые необходимо преобразовать
     * @return список преобразованных объектов OrderNumber
     */
    List<OrderNumber> toEntityList(List<GeneratedNumberDTO> orderNumberDTOs);
}
