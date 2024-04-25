package com.app.ConStructCompany.Controller;


import com.app.ConStructCompany.Response.HomeResponse;
import com.app.ConStructCompany.Service.CustomerService;
import com.app.ConStructCompany.Service.OrderService;
import com.app.ConStructCompany.Service.ProductService;
import com.app.ConStructCompany.Service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {
    private final OrderService orderService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final StatisticService statisticService;
    @GetMapping("/getNbr")
    public ResponseEntity<?> getNbr(){
        List<HomeResponse> list = new ArrayList<>();
        list.add(new HomeResponse("product", (double) productService.countProduct(),1));
        list.add(new HomeResponse("customer", (double) customerService.countCustomer(),2));
        list.add(new HomeResponse("order",(double) orderService.countOrders(),3));
        list.add(new HomeResponse("statistic",(double)statisticService.countStatistic(),4));
        list.add(new HomeResponse("payment",orderService.calLeftAmount(),5));
        return ResponseEntity.ok().body(list);
    }
}
