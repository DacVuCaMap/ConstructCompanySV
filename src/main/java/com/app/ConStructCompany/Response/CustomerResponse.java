package com.app.ConStructCompany.Response;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerResponse {
    private Long id;
    private String companyName;
    private String address;
    private String taxCode;
    private Double debt;
    private Date createAt;
    private Date updateAt;
    private Double totalPayment;
    private String phoneNumber;
    private String positionCustomer;
    private String representativeCustomer;
    private String email;
}
