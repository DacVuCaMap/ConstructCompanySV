package com.app.ConStructCompany.Request;

import lombok.Data;

@Data
public class StatisticRequest {
    private Long id;
    private Long customerId;
    private Long sellerId;
    private String representativeCustomer;
    private String representativeSeller;
    private String positionCustomer;
    private String positionSeller;
    private Double totalAmount;
}
