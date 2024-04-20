package com.app.ConStructCompany.Request;

import lombok.Data;

@Data
public class SellerRequest {
    private String companyName;
    private String address;
    private String taxCode;
    private String representativeSeller;
    private String positionSeller;
    private String phoneNumber;
    private String accountBankName;
    private String accountBankNumber;
    private String bankName;
}
