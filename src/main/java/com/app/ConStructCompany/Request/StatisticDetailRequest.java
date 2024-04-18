package com.app.ConStructCompany.Request;

import lombok.Data;

import java.util.Date;
@Data
public class StatisticDetailRequest {
    private Long statisticDetailId;
    private Date day;
    private String licensePlate;
    private String trailer;
    private String ticket;
    private String typeProduct;
    private String unit;
    private Long proId;
    private Double materialWeight;
    private Double price;
    private Double totalAmount;
    private String note;
}
