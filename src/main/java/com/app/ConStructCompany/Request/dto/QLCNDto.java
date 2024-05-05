package com.app.ConStructCompany.Request.dto;

import com.app.ConStructCompany.Entity.Customer;
import lombok.Data;

@Data
public class QLCNDto {
    private Customer customer;
    private Long orderCount;
    private Double TotalLeftAmount;
}
