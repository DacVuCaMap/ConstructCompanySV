package com.app.ConStructCompany.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String mess;
    private boolean status;
}
