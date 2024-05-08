package com.app.ConStructCompany.Request;

import lombok.Data;

import java.util.Date;

@Data
public class StatisticRequest {
    private Long id;
    private Long customerId;
    private Long sellerId;
    private Long orderId;
    private String representativeCustomer;
    private String representativeSeller;
    private String positionCustomer;
    private String positionSeller;
    private Double totalAmount;
    private Date endDay;
    private Date startDay;
}
