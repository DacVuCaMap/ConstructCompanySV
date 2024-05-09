package com.app.ConStructCompany.Request.dto;

import lombok.Data;

@Data
public class ImportOrderDto {
    private Long productId;
    private String name;
    private String unit;
    private Double materialWeight;

}
