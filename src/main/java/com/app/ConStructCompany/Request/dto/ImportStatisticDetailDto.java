package com.app.ConStructCompany.Request.dto;

import lombok.Data;

@Data
public class ImportStatisticDetailDto {
    private Long productId;
    private String date;
    private String licensePlate;
    private String trailersLicensePlate;
    private String number;
    private String typeProduct;
    private Double materialWeight;
    private Double price;
}
