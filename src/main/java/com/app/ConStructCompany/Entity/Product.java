package com.app.ConStructCompany.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "product")
@Data
@Getter
@Setter
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "proName")
    private String proName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "inventory")
    private Double inventory;

    @Column(name = "price")
    private Double price;

    @Column(name = "importPrice")
    private Double importPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "update_at")
    private Date updateAt;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "deleted")
    private boolean deleted;
}
