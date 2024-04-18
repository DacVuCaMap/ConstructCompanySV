package com.app.ConStructCompany.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetIsPaymentedRequest {

    @NotNull(message = "ID đơn hàng là bắt buộc")
    private Long id;

    @NotNull(message = "Trạng thái thanh toán là bắt buộc")
    private Boolean payment;
}
