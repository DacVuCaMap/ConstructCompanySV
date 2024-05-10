package com.app.ConStructCompany.Request.dto;

import com.app.ConStructCompany.Entity.Product;
import lombok.Data;

@Data
public class ImportStatisticDetailDto {
    private Product product;
    private String date;
    private String licensePlate;
    private String trailersLicensePlate;
    private String number;
    private String typeProduct;
    private Double materialWeight;
    private Double price;
}
