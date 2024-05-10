package com.app.ConStructCompany.Request.dto;

import com.app.ConStructCompany.Entity.Product;
import lombok.Data;

@Data
public class ImportOrderDto {
    private Product product;
    private String name;
    private String unit;
    private Double materialWeight;

}
