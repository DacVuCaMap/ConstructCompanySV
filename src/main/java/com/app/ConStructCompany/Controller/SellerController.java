package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Entity.Seller;
import com.app.ConStructCompany.Repository.SellerRepository;
import com.app.ConStructCompany.Request.SellerRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

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
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSeller (@PathVariable Long id){
        Optional<Seller> seller = sellerRepository.findById(id);
        if (seller.isEmpty()){
            return ResponseEntity.badRequest().body("Khong ton tai seller");
        }
        return ResponseEntity.ok(seller);
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateSeller(@RequestBody @Valid SellerRequest sellerRequest){
        Seller seller = new Seller();
        BeanUtils.copyProperties(sellerRequest,seller);
        seller.setCreate_at(new Date());
        sellerRepository.save(seller);
        return ResponseEntity.ok().body("success");
    }
}
