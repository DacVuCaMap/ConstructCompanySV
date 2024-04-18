package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sec/")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @GetMapping("/accounts")
    public ResponseEntity<?>getAll(){
        return ResponseEntity.ok(accountService.getAll());
    }
}
