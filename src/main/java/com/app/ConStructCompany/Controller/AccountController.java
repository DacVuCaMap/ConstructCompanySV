package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Request.dto.StatisticDTO;
import com.app.ConStructCompany.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/accounts")
    public ResponseEntity<?>getAll(){
        return ResponseEntity.ok(accountService.getAll());
    }
//    @GetMapping("/list")
//    public ResponseEntity<?> getAccList (@RequestParam Integer size, @RequestParam Integer page){
//        PageRequest pageRequest = PageRequest.of(page,size);
////        Page<StatisticDTO> statisticPage = statisticService.findAll(pageRequest);
////        return ResponseEntity.ok(statisticPage);
//    }
}
