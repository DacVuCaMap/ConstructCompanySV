package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Entity.Seller;
import com.app.ConStructCompany.Repository.SellerRepository;
import com.app.ConStructCompany.Request.SellerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {
    private final SellerRepository sellerRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addSeller(@RequestBody SellerRequest sellerRequest){
        Seller seller = new Seller();
        BeanUtils.copyProperties(sellerRequest,seller);
        seller.setCreate_at(new Date());
        seller.setId(1L);
        System.out.println(seller);
        sellerRepository.save(seller);
        return ResponseEntity.ok("ok");
    }
}
