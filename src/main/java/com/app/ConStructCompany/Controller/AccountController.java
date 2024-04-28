package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Request.dto.AccountDTO;
import com.app.ConStructCompany.Request.dto.StatisticDTO;
import com.app.ConStructCompany.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account/")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/list")
    public ResponseEntity<?> getAccList (@RequestParam Integer size, @RequestParam Integer page){
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<AccountDTO> accountDTO = accountService.findAccountByPageSize(pageRequest);
        return ResponseEntity.ok(accountDTO);
    }
    @GetMapping("/del/{id}")
    public ResponseEntity<?> delAccList (@PathVariable Long id){  
        return accountService.delAcc(id);
    }

}
