package com.app.ConStructCompany.Service.impl;

import com.app.ConStructCompany.Entity.Order;
import com.app.ConStructCompany.Entity.OrderDetail;
import com.app.ConStructCompany.Repository.OrderDetailRepository;
import com.app.ConStructCompany.Repository.OrderRepository;
import com.app.ConStructCompany.Repository.ProductRepository;
import com.app.ConStructCompany.Request.AddOrderRequest;
import com.app.ConStructCompany.Request.dto.ProductOrderDetailDto;
import com.app.ConStructCompany.Response.GetOrderDetailsResponse;
import com.app.ConStructCompany.Response.PostOrderResponse;
import com.app.ConStructCompany.Service.OrderDetailsService;
import com.app.ConStructCompany.Service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    @Override
    public GetOrderDetailsResponse getOrderDetails(Long id) {
        Optional<Order> checkOrder = orderRepository.findById(id);
        if (!checkOrder.isPresent()){
            return new GetOrderDetailsResponse(HttpStatus.BAD_REQUEST.value(), "Không tìm thấy đơn hàng", null, null);
        }
        Order order = checkOrder.get();

        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(id);

        List<ProductOrderDetailDto> productOrderDetailDtos = orderDetails.stream()
                .map(ProductOrderDetailDto::getProductOrderDetail)
                .toList();

        GetOrderDetailsResponse response = new GetOrderDetailsResponse();
        response.setOrder(order);
        response.setOrderDetails(productOrderDetailDtos);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Thành công");

        return response;
    }
}
