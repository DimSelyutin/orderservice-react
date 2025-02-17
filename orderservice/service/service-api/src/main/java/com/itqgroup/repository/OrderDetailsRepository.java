package com.itqgroup.repository;

import com.itqgroup.entity.OrderDetail;

import java.util.List;

public interface OrderDetailsRepository {

    void createOrderDetail(OrderDetail orderDetail);

    List<OrderDetail> getOrderDetailsByOrderId(int orderId);
}