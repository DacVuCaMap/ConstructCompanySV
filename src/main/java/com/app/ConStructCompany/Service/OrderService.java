package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Request.AddOrderRequest;
import com.app.ConStructCompany.Request.EditOrderRequest;
import com.app.ConStructCompany.Request.GetOrdersRequest;
import com.app.ConStructCompany.Request.SetIsPaymentedRequest;
import com.app.ConStructCompany.Response.PostOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface OrderService {
    @Transactional
    PostOrderResponse addOrder(AddOrderRequest addOrderRequest);
    PostOrderResponse editOrder(EditOrderRequest editOrderRequest);
    ResponseEntity getOrders(GetOrdersRequest getOrdersRequest);
    PostOrderResponse deleteOrder(Long id);
    PostOrderResponse setIsPaymented(SetIsPaymentedRequest setIsPaymentedRequest);
}
