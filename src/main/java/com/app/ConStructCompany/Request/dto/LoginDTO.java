package com.app.ConStructCompany.Request.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Long id;
    private String email;
    private String fullName;
    private boolean isAdmin;
    private String token;
    private boolean isRemember;
}
