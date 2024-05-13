package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Order;
import com.app.ConStructCompany.Request.AddOrderRequest;
import com.app.ConStructCompany.Request.EditOrderRequest;
import com.app.ConStructCompany.Request.GetOrdersRequest;
import com.app.ConStructCompany.Request.SetIsPaymentedRequest;
import com.app.ConStructCompany.Request.dto.OrderDto;
import com.app.ConStructCompany.Response.OrderListResponse;
import com.app.ConStructCompany.Response.OrderResponse;
import com.app.ConStructCompany.Response.PostOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface OrderService {
    @Transactional
    PostOrderResponse addOrder(AddOrderRequest addOrderRequest);
    PostOrderResponse editOrder(EditOrderRequest editOrderRequest);
    ResponseEntity getOrders(GetOrdersRequest getOrdersRequest);
    PostOrderResponse deleteOrder(Long id);
    PostOrderResponse setIsPaymented(SetIsPaymentedRequest setIsPaymentedRequest);

    OrderDto convertToOrderDto(Order order);
    OrderResponse convertToOrderResponse(Order order);
    Double calLeftAmount();
    int countOrders();

    List<OrderListResponse> listOrderByCusId(Long cusId);
}
