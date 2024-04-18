package com.app.ConStructCompany.Request;

import com.app.ConStructCompany.Request.dto.OrderDetailDto;
import com.app.ConStructCompany.Request.dto.OrderDto;
import lombok.Data;

import java.util.List;

@Data
public class EditOrderRequest {

    private OrderDto order;
    private List<OrderDetailDto> orderDetails;

    public Boolean isValidRequest() {
        if (order != null && orderDetails != null &&
                !(Double.isNaN(order.getTotalAmount()) || order.getTotalAmount() < 0 ||
                        Double.isNaN(order.getTotalCost()) || order.getTotalCost() < 0 ||
                        Double.isNaN(order.getTax()) || order.getTax() < 0 ||
                        order.getPositionCustomer() == null || order.getPositionCustomer().isEmpty() ||
                        order.getPositionSeller() == null || order.getPositionSeller().isEmpty() ||
                        order.getRepresentativeCustomer() == null || order.getRepresentativeCustomer().isEmpty() ||
                        order.getRepresentativeSeller() == null || order.getRepresentativeSeller().isEmpty() ||
                        order.getContractCode() == null || order.getContractCode().isEmpty() ||
                        order.getSigningDate() == null ||
                        order.getCustomerId() == null ||
                        order.getSellerId() == null ||
                        order.getId() == null ||
                        orderDetails.isEmpty() ||
                        orderDetails.stream().anyMatch(detail -> detail.getProductId() == null ||
                                Double.isNaN(detail.getMaterialWeight()) || detail.getMaterialWeight() < 0 ||
                                Double.isNaN(detail.getPrice()) || detail.getPrice() < 0)

                )) {
            return true;
        }
        return false;
    }

}
