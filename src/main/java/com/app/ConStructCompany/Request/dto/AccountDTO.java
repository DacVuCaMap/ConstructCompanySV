package com.app.ConStructCompany.Request.dto;

import lombok.Data;

import java.util.Date;
@Data
public class AccountDTO {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Date createAt;
    private boolean isAdmin;
}
