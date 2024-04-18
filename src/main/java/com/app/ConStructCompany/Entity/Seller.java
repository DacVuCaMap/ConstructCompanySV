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
    @Column(name = "create_at")
    private Date create_at;


}