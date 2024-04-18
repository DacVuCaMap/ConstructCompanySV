package com.app.ConStructCompany.Response;

import com.app.ConStructCompany.Entity.Statistic;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class StatisticDetailResponse {
    private Long id;
    private Long statisticID;
    private Date day;
    private String licensePlate;
    private String trailer;
    private String ticket;
    private String typeProduct;
    private Double materialWeight;
    private Double price;
    private Double totalAmount;
    private String note;
    private Long proId;
    private String unit;

}
