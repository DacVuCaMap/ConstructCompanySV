package com.app.ConStructCompany.Exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ValidateException extends RuntimeException{
    private HttpStatus code;
    private String message;
}
