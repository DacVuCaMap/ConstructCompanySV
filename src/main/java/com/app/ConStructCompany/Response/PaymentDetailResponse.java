package com.app.ConStructCompany.Response;

import com.app.ConStructCompany.Entity.Customer;
import com.app.ConStructCompany.Entity.Order;
import com.app.ConStructCompany.Entity.Payment;
import com.app.ConStructCompany.Request.dto.OrderDto;
import com.app.ConStructCompany.Request.dto.PaymentDTO;
import lombok.Data;

import java.util.List;

@Data
public class PaymentDetailResponse {
    private List<PaymentDTO> payments;
    private StatisticResponse statistic;
}
