package com.itqgruop.facade.impl;

import com.itqgroup.openapi.model.GeneratedNumberDTO;
import com.itqgruop.entity.OrderNumber;
import com.itqgruop.facade.OrderNumberFacade;
import com.itqgruop.mapper.OrderNumberMapper;
import com.itqgruop.service.OrderNumberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderNumberFacadeImpl implements OrderNumberFacade {

    private final OrderNumberService orderNumberService;
    private final OrderNumberMapper orderNumberMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public GeneratedNumberDTO generateUniqueOrderNumber() {
        log.info("Method generateUniqueOrderNumber - start");
        OrderNumber orderNumber = orderNumberService.generateUniqueOrderNumber();
        log.info("Generated OrderNumber: {}", orderNumber);
        GeneratedNumberDTO generatedNumberDTO = orderNumberMapper.toDto(orderNumber);
        log.info("Method generateUniqueOrderNumber - end, orderNumber:{}", orderNumber.getNumber());

        return generatedNumberDTO;
    }
}
