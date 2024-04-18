package com.app.ConStructCompany.Request.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class StatisticDTO {
    private Long id;
    private String companyName;
    private Date createAt;
    private Date updateAt;
    private Double totalAmount;
}
