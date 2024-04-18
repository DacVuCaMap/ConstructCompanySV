package com.app.ConStructCompany.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "statistic")
@Data
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cusId")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerId")
    private Seller seller;

    @Column(name = "representativeCustomer")
    private String representativeCustomer;

    @Column(name = "positionCustomer")
    private String positionCustomer;

    @Column(name = "representativeSeller")
    private String representativeSeller;

    @Column(name = "positionSeller")
    private String positionSeller;

    @Column(name = "totalAmount")
    private Double totalAmount;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private Date deletedAt;
}
