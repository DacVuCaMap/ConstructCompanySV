package com.app.ConStructCompany.Request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class ProductEditRequest {
    @NotNull(message = "ID sản phẩm không được để trống")
    private Long id;

    @NotNull(message = "Tên sản phẩm không được để trống")
    private String proName;

    @NotNull(message = "Khong phu hop")
    private Double inventory;

    @NotNull(message = "Unit không được để trống")
    private String unit;

    @NotNull(message = "Giá sản phẩm không được để trống")
    private Double price;

    @NotNull(message = "Giá nhập không được để trống")
    private Double importPrice;

    private String description;


}
