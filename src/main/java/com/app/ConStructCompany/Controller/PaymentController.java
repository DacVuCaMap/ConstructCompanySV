package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Entity.Order;
import com.app.ConStructCompany.Entity.Payment;
import com.app.ConStructCompany.Repository.OrderRepository;
import com.app.ConStructCompany.Request.RequestPayment;
import com.app.ConStructCompany.Request.dto.PaymentDTO;
import com.app.ConStructCompany.Response.GetOrderDetailsResponse;
import com.app.ConStructCompany.Response.PaymentDetailResponse;
import com.app.ConStructCompany.Service.CustomerService;
import com.app.ConStructCompany.Service.OrderDetailsService;
import com.app.ConStructCompany.Service.OrderService;
import com.app.ConStructCompany.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    @PostMapping("/add")
    public ResponseEntity<?> addPayment(@RequestBody RequestPayment requestPayment){
        paymentService.addPayment(requestPayment);
        return ResponseEntity.ok().body("success");
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPayment (@PathVariable  Long id){
        List<PaymentDTO> paymentDTOList = paymentService.getAllPaymentDTO(id);
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()){
            return ResponseEntity.badRequest().body("order khong ton tai");
        }
        PaymentDetailResponse paymentDetailResponse = new PaymentDetailResponse();

        paymentDetailResponse.setPayments(paymentDTOList);
        paymentDetailResponse.setOrder(orderService.convertToOrderResponse(orderOptional.get()));
//        System.out.println(paymentDetailResponse);
        return ResponseEntity.ok().body(paymentDetailResponse);
    }
}
