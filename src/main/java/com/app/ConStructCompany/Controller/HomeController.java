package com.app.ConStructCompany.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @GetMapping("/getNbr")
    public ResponseEntity<?> getNbr(){

        return ResponseEntity.ok().body("d");
    }
}
