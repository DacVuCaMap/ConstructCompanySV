package com.app.ConStructCompany.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "representative_customer")
    private String representativeCustomer;

    @Column(name = "representative_seller")
    private String representativeSeller;

    @Column(name = "position_customer")
    private String positionCustomer;

    @Column(name = "position_seller")
    private String positionSeller;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sellerId")
    private Seller seller;

    @Column(name="total_cost")
    private Double totalCost;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "is_paymented")
    private Boolean isPaymented;

    @Column(name = "update_at")
    private Date updateAt;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name="contract_code")
    private String contractCode;

    @Column(name = "signing_date")
    private String signingDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "delete_at")
    private Date deleteAt;

    @Column(name = "leftAmount")
    private Double leftAmount;

}
