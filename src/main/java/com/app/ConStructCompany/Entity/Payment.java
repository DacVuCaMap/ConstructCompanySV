package com.app.ConStructCompany.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;
    @Column(name = "price")
    private Double price;
    @Column(name = "day")
    private Date day;
    @Column(name = "createAt")
    private Date createAt;
}
