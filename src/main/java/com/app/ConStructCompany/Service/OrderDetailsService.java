package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Response.GetOrderDetailsResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderDetailsService {
    GetOrderDetailsResponse getOrderDetails(Long id);
}
