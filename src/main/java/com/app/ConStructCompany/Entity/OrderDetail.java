package com.app.ConStructCompany.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;

@Entity
@Table(name = "orderDetail")
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "material_weight")
    private Double materialWeight;

    @Column(name = "price")
    private Double price;

}
