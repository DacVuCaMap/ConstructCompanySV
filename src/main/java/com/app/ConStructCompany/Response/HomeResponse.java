package com.app.ConStructCompany.Response;

import lombok.Data;

import java.util.Map;

@Data
public class HomeResponse {
    private String name;
    private Double value;
    private int key;
    public HomeResponse(String name, Double value,int key) {
        this.key = key;
        this.name = name;
        this.value = value;
    }
}
