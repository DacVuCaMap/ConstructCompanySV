package com.app.ConStructCompany.Request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "name is required")
    private String email;
    @NotNull
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;
    @NotNull
    private String fullName;
    @Pattern(regexp = "^[0-9]+$",message = "must be number")
    private String phoneNumber;
    @NotNull
    private boolean isAdmin;
}
