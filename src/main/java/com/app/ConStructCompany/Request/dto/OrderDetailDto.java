package com.app.ConStructCompany.Request.dto;

import lombok.Data;

@Data
public class OrderDetailDto {
    private Long orderDetailId;
    private Long productId;
    private Double materialWeight;
    private Double price;
}
