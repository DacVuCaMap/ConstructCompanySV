package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Response.GetOrderDetailsResponse;
import com.app.ConStructCompany.Service.OrderDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order/details/")
@AllArgsConstructor
public class OrderDetailsController {
    private final OrderDetailsService orderDetailsService;
    @GetMapping("/{id}")
    public GetOrderDetailsResponse getOrderDetails(@PathVariable Long id){
        return orderDetailsService.getOrderDetails(id);
    }
}
