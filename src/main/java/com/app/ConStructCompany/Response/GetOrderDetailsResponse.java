package com.app.ConStructCompany.Response;

import com.app.ConStructCompany.Entity.Order;
import com.app.ConStructCompany.Entity.OrderDetail;
import com.app.ConStructCompany.Entity.Product;
import com.app.ConStructCompany.Request.dto.ProductOrderDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderDetailsResponse {
    private int status;
    private String message;
    private Order order;
    private List<ProductOrderDetailDto> orderDetails;
}
