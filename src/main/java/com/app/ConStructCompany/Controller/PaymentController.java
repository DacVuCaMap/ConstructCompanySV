package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Entity.Order;
import com.app.ConStructCompany.Entity.Payment;
import com.app.ConStructCompany.Entity.Statistic;
import com.app.ConStructCompany.Repository.OrderRepository;
import com.app.ConStructCompany.Repository.StatisticRepository;
import com.app.ConStructCompany.Request.RequestPayment;
import com.app.ConStructCompany.Request.dto.PaymentDTO;
import com.app.ConStructCompany.Response.GetOrderDetailsResponse;
import com.app.ConStructCompany.Response.PaymentDetailResponse;
import com.app.ConStructCompany.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final StatisticRepository statisticRepository;
    private final StatisticService statisticService;
    @PostMapping("/add")
    public ResponseEntity<?> addPayment(@RequestBody RequestPayment requestPayment){
        Optional<Statistic> statistic = statisticRepository.findById(requestPayment.getStatisticId());
        if (statistic.isEmpty()){
            return ResponseEntity.badRequest().body("Khong ton tai");
        }
        paymentService.addPayment(requestPayment);
        statisticService.updateAllStatisticByOrder(statistic.get().getOrder().getId());
        return ResponseEntity.ok().body("success");
    }
    @PostMapping("/addone")
    public ResponseEntity<?> addOnePayment(@RequestBody PaymentDTO paymentDTO){

        paymentService.addOnePayment(paymentDTO);
        statisticService.updateAllStatisticByOrder(paymentDTO.getOrderId());
        return ResponseEntity.ok().body("success");
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPayment (@PathVariable Long id){
        List<PaymentDTO> paymentDTOList = paymentService.getAllPaymentDTO(id);
        Optional<Statistic> statisticOptional = statisticRepository.findById(id);
        if (statisticOptional.isEmpty()){
            return ResponseEntity.badRequest().body("order khong ton tai");
        }
        PaymentDetailResponse paymentDetailResponse = new PaymentDetailResponse();

        paymentDetailResponse.setPayments(paymentDTOList);
        paymentDetailResponse.setStatistic(statisticService.convertStatisticResponse(statisticOptional.get()));
//        System.out.println(paymentDetailResponse);
        return ResponseEntity.ok().body(paymentDetailResponse);
    }
//    @GetMapping("/list")
//    public ResponseEntity<?> getListByOrder (@RequestParam Long search){
//        paymentService
//    }
}
