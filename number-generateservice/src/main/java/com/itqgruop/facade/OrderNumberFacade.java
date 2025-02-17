package com.itqgruop.facade;

import com.itqgroup.openapi.model.GeneratedNumberDTO;
import org.springframework.stereotype.Component;

public interface OrderNumberFacade {

    GeneratedNumberDTO generateUniqueOrderNumber();
}
