package com.app.ConStructCompany.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "address")
    private String address;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "debt")
    private Double debt;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    @Column(name = "is_deleted")
    @ColumnDefault("false")
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "total_payment")
    private Double totalPayment;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "position_customer")
    private String positionCustomer;

    @Column(name = "representative_customer")
    private String representativeCustomer;
    @Column (name="email")
    private String email;
    @Column(name = "totalDebt")
    private Double totalDebt;
    @Column(name = "payDebt")
    private Double payDebt;
}