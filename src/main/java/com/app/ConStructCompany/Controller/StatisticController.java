package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Entity.Statistic;
import com.app.ConStructCompany.Request.StatisticAddRequest;
import com.app.ConStructCompany.Request.StatisticRequest;
import com.app.ConStructCompany.Request.dto.StatisticDTO;
import com.app.ConStructCompany.Response.GetStatisticResponse;
import com.app.ConStructCompany.Service.StatisticService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
    private StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getStatistic(@RequestParam Integer size, @RequestParam Integer page) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<StatisticDTO> statisticPage = statisticService.findAll(pageRequest);
        return ResponseEntity.ok(statisticPage);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStatistic(@RequestBody StatisticAddRequest statisticAddRequest) {
        Statistic statistic = statisticService.addStatistic(statisticAddRequest);
        if(statistic!=null){
            return ResponseEntity.ok("add thanh cong");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id của khách hàng hoặc người bán!");
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editStatistic(@RequestBody StatisticAddRequest statisticAddRequest) {
        return statisticService.editStatistic(statisticAddRequest);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteStatistic(@RequestParam Long id) {
        return statisticService.deleteStatistic(id);
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getStatisticDetails (@PathVariable Long id){
        System.out.println(id);
        return statisticService.getDetailsStatistic(id);
    }
}
