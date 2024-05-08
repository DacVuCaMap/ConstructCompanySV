package com.app.ConStructCompany.Response;

import com.app.ConStructCompany.Entity.Customer;
import com.app.ConStructCompany.Entity.Seller;
import com.app.ConStructCompany.Request.dto.OrderDto;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class StatisticResponse {
    private Long id;
    private CustomerResponse customer;
    private OrderDto order;
    private Long sellerId;
    private String representativeCustomer;
    private String positionCustomer;
    private String representativeSeller;
    private String positionSeller;
    private Double totalAmount;
    private Date createAt;
    private Date updateAt;
    private Date endDay;
    private Date startDay;
    private Double cashLeft;

}
