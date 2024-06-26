package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Entity.Statistic;
import com.app.ConStructCompany.Request.StatisticAddRequest;
import com.app.ConStructCompany.Request.StatisticRequest;
import com.app.ConStructCompany.Request.dto.StatisticDTO;
import com.app.ConStructCompany.Response.GetStatisticResponse;
import com.app.ConStructCompany.Service.PaymentService;
import com.app.ConStructCompany.Service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;
    private final PaymentService paymentService;


    @GetMapping("/get")
    public ResponseEntity<?> getStatistic(@RequestParam Integer size, @RequestParam Integer page,@RequestParam String search) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<StatisticDTO> statisticPage = statisticService.findAll(pageRequest,search);
        return ResponseEntity.ok(statisticPage);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStatistic(@RequestBody StatisticAddRequest statisticAddRequest) {
        Statistic statistic = statisticService.addStatistic(statisticAddRequest);
        if(statistic!=null){
            statisticService.updateAllStatisticByOrder(statistic.getOrder().getId());
            return ResponseEntity.ok("add thanh cong");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id của khách hàng hoặc người bán!");
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editStatistic(@RequestBody StatisticAddRequest statisticAddRequest) {
        System.out.println(statisticAddRequest);
        statisticService.editStatistic(statisticAddRequest);
        statisticService.updateAllStatisticByOrder(statisticAddRequest.getStatistic().getOrderId());
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteStatistic(@RequestParam Long id) {
        return statisticService.deleteStatistic(id);
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getStatisticDetails (@PathVariable Long id){
        return statisticService.getDetailsStatistic(id);
    }
    @GetMapping("/listbyorder")
    public ResponseEntity<?> getStatisticByOrderId(@RequestParam Long id){
        List<StatisticDTO> statistics = paymentService.getStatisticByOrder(id);
        if (statistics.isEmpty()){
            return ResponseEntity.badRequest().body("Khong co");
        }
        return ResponseEntity.ok().body(statistics);
    }
}
