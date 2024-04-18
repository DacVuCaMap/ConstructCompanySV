package com.app.ConStructCompany.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCustomerResponse {
    private int status;
    private String message;
}