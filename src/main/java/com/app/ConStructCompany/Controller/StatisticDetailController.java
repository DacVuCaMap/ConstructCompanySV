package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Entity.Statistic;
import com.app.ConStructCompany.Entity.StatisticDetail;
import com.app.ConStructCompany.Request.StatisticDetailRequest;
import com.app.ConStructCompany.Response.StatisticDetailResponse;
import com.app.ConStructCompany.Service.StatisticDetailService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statisticDetail")
@AllArgsConstructor
public class StatisticDetailController {
    private final StatisticDetailService statisticDetailService;


    @GetMapping("/get")
    public ResponseEntity<?> getStatisticDetail(@RequestParam Integer size, @RequestParam Integer page) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<StatisticDetailResponse> statisticDetailResponsePage = statisticDetailService.getStatisticDetail(pageRequest);
        return ResponseEntity.ok(statisticDetailResponsePage);
    }

}
