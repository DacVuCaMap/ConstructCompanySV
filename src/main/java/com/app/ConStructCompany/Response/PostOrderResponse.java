package com.app.ConStructCompany.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostOrderResponse {
    private int status;
    private String message;
}
