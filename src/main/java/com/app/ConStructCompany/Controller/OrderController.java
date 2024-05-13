package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Request.AddOrderRequest;
import com.app.ConStructCompany.Request.EditOrderRequest;
import com.app.ConStructCompany.Request.GetOrdersRequest;
import com.app.ConStructCompany.Request.SetIsPaymentedRequest;
import com.app.ConStructCompany.Response.OrderListResponse;
import com.app.ConStructCompany.Response.PostOrderResponse;
import com.app.ConStructCompany.Service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order/")
@AllArgsConstructor
public class OrderController {
    public final OrderService orderService;
    @PostMapping("/add-order")
    public PostOrderResponse addOrder(@RequestBody AddOrderRequest addOrderRequest){
        return orderService.addOrder(addOrderRequest);
    }

    @GetMapping("/list")
    public ResponseEntity getOrders(GetOrdersRequest getOrdersRequest){
        return orderService.getOrders(getOrdersRequest);
    }

    @PostMapping("/edit-order")
    public PostOrderResponse editOrder(@RequestBody EditOrderRequest editOrderRequest){
//        System.out.println(editOrderRequest);
        return orderService.editOrder(editOrderRequest);
    }

    @PostMapping("/delete-order")
    public PostOrderResponse deleteOrder(@RequestParam Long id){
        return orderService.deleteOrder(id);
    }

    @PostMapping("/set-payment")
    public PostOrderResponse setIsPaymented(@Valid SetIsPaymentedRequest setIsPaymentedRequest){
        return orderService.setIsPaymented(setIsPaymentedRequest);
    }
    @GetMapping("/get-order-by-cus")
    public ResponseEntity<?> getOrderByCus(@RequestParam Long id){
        List<OrderListResponse> orderListResponses = orderService.listOrderByCusId(id);
        if (orderListResponses==null){
            return ResponseEntity.badRequest().body("Lỗi");
        }
        return ResponseEntity.ok().body(orderListResponses);
    }
}
