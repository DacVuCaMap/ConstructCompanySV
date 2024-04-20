package com.app.ConStructCompany.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "companyName")
    private String companyName;
    @Column(name = "address")
    private String address;
    @Column(name = "taxCode")
    private String taxCode;
    @Column(name = "representativeSeller")
    private String representativeSeller;
    @Column(name = "positionSeller")
    private String positionSeller;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column (name = "accountBankName")
    private String accountBankName;
    @Column (name = "accountBankNumber")
    private String accountBankNumber;
    @Column (name = "bankName")
    private String bankName;
    @Column(name = "create_at")
    private Date create_at;


}