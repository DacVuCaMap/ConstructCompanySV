package com.app.ConStructCompany.Request.dto;

import com.app.ConStructCompany.Entity.Customer;
import com.app.ConStructCompany.Entity.Payment;
import com.app.ConStructCompany.Response.CustomerResponse;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StatisticDTO {
    private Long id;
    private OrderDto order;
    private CustomerResponse customer;
    private Date endDay;
    private Date startDay;
    private Double cashLeft;
    private String companyName;
    private Date createAt;
    private Date updateAt;
    private Double totalAmount;
    private Double totalPay;
    private List<PaymentDTO> payments;
}
