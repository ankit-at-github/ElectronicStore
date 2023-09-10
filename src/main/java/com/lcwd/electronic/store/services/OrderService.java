package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    /*
    Logics related to API calling
     */
    //Create Order
    OrderDto createOrder(CreateOrderRequest createOrderRequest);

    //Remove Order
    void removeOrder(String orderId);

    //Get Order of Users
    List<OrderDto> getOrdersOfUser(String userId);

    //Get Orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Other methods(logic) related to Order
}
