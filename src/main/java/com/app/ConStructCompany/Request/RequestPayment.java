package com.app.ConStructCompany.Request;

import com.app.ConStructCompany.Request.dto.PaymentDTO;
import lombok.Data;

import java.util.List;

@Data
public class RequestPayment {
    private List<PaymentDTO> payments;
    private Long orderId;
}
