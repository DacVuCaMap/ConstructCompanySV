package com.app.ConStructCompany.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SellerRequest {
    @NotNull
    private Long id;
    @NotNull
    private String companyName;
    @NotNull
    private String address;
    @NotNull
    private String taxCode;
    @NotNull
    private String representativeSeller;
    @NotNull
    private String positionSeller;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String accountBankName;
    @NotNull
    private String accountBankNumber;
    @NotNull
    private String bankName;
}
