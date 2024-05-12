package com.app.ConStructCompany.Request.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class StatisticDTO {
    private Long id;
    private Date endDay;
    private Date startDay;
    private Double cashLeft;
    private String companyName;
    private Date createAt;
    private Date updateAt;
    private Double totalAmount;
    private Double totalPay;
}
