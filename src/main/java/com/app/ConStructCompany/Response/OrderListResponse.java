package com.app.ConStructCompany.Response;

import com.app.ConStructCompany.Request.dto.OrderDto;
import com.app.ConStructCompany.Request.dto.StatisticDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrderListResponse {
    private OrderDto order;
    private List<StatisticDTO> statistics;
}
