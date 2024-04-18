package com.app.ConStructCompany.Request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class LoginRequest {
    private String email;
    private String password;
    private boolean isRemember;
}
