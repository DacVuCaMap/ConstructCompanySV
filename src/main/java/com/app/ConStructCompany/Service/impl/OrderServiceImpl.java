package com.app.ConStructCompany.Service.impl;

import com.app.ConStructCompany.Entity.*;
import com.app.ConStructCompany.Repository.*;
import com.app.ConStructCompany.Request.AddOrderRequest;
import com.app.ConStructCompany.Request.EditOrderRequest;
import com.app.ConStructCompany.Request.GetOrdersRequest;
import com.app.ConStructCompany.Request.SetIsPaymentedRequest;
import com.app.ConStructCompany.Request.dto.OrderDetailDto;
import com.app.ConStructCompany.Request.dto.OrderDto;
import com.app.ConStructCompany.Response.OrderResponse;
import com.app.ConStructCompany.Response.PostOrderResponse;
import com.app.ConStructCompany.Service.OrderService;
import com.app.ConStructCompany.Service.PaymentService;
import com.app.ConStructCompany.utils.DateTimeUtils;
import com.app.ConStructCompany.utils.GenerateUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public PostOrderResponse addOrder(AddOrderRequest addOrderRequest) {
        try {
            if (!addOrderRequest.isValidRequest()){
                throw new IllegalArgumentException("Các trường nhập là bắt buộc và nhập giá hoặc thuế phải là số");
            }

            Optional<Customer> checkCustomer = customerRepository.findById(addOrderRequest.getOrder().getCustomerId());
            if (!checkCustomer.isPresent()){
                throw new IllegalArgumentException("Không có khách hàng");
            }
            Customer customer = checkCustomer.get();

            Optional<Seller> checkSeller = sellerRepository.findById(addOrderRequest.getOrder().getSellerId());
            if (!checkSeller.isPresent()){
                throw new IllegalArgumentException("Không có người bán");
            }
            Seller seller = checkSeller.get();

            Long latestOrderCode = getLatesId();
            String newOrderCode = GenerateUtils.generateOrderCode(latestOrderCode);
            Order order = new Order();
            order.setPositionCustomer(addOrderRequest.getOrder().getPositionCustomer());
            order.setPositionSeller(addOrderRequest.getOrder().getPositionSeller());
            order.setRepresentativeCustomer(addOrderRequest.getOrder().getRepresentativeCustomer());
            order.setRepresentativeSeller(addOrderRequest.getOrder().getRepresentativeSeller());
            order.setTax(addOrderRequest.getOrder().getTax());
            order.setTotalCost(addOrderRequest.getOrder().getTotalCost());
            order.setTotalAmount(addOrderRequest.getOrder().getTotalAmount());
            order.setContractCode(addOrderRequest.getOrder().getContractCode());
            order.setSigningDate(addOrderRequest.getOrder().getSigningDate());
            order.setIsPaymented(false);
            order.setIsDeleted(false);
            order.setCustomer(customer);
            order.setSeller(seller);
            order.setCreateAt(DateTimeUtils.getCurrentDate());
            order.setOrderCode(newOrderCode);
            order.setLeftAmount(addOrderRequest.getOrder().getTotalAmount());
            Order newOrder = orderRepository.save(order);
            newOrder.setContractCode(order.getContractCode()+newOrder.getId());
            newOrder = orderRepository.save(newOrder);
            List<OrderDetail> orderDetails = new ArrayList<>();

            List<OrderDetailDto> orderDetailDtos = addOrderRequest.getOrderDetails();

            for (OrderDetailDto orderDetailDto : orderDetailDtos) {
                Optional<Product> checkProduct = productRepository.findById(orderDetailDto.getProductId());
                if (!checkProduct.isPresent()){
                    throw new IllegalArgumentException("Sản phẩm không tồn tại: " + orderDetailDto.getProductId());
                }
                Product product = checkProduct.get();
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(newOrder);
                orderDetail.setPrice(orderDetailDto.getPrice());
                orderDetail.setMaterialWeight(orderDetailDto.getMaterialWeight());
                orderDetail.setProduct(product);

                orderDetails.add(orderDetail);
            }

            orderDetailRepository.saveAll(orderDetails);
            Double debt = customer.getDebt()==null ? 0 : customer.getDebt();
            customer.setDebt(debt + addOrderRequest.getOrder().getTotalAmount());
            customerRepository.save(customer);

            return new PostOrderResponse(HttpStatus.OK.value(), "Thêm đơn hàng thành công");
        }catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    @Transactional
    public PostOrderResponse editOrder(EditOrderRequest editOrderRequest) {
        try {
            if (!editOrderRequest.isValidRequest()){
                throw new IllegalArgumentException("Các trường nhập là bắt buộc và nhập giá hoặc thuế phải là số");
            }

            Optional<Customer> checkCustomer = customerRepository.findById(editOrderRequest.getOrder().getCustomerId());
            if (!checkCustomer.isPresent()){
                throw new IllegalArgumentException("Không có khách hàng");
            }
            Customer customer = checkCustomer.get();

            Optional<Seller> checkSeller = sellerRepository.findById(editOrderRequest.getOrder().getSellerId());
            if (!checkSeller.isPresent()){
                throw new IllegalArgumentException("Không có người bán");
            }
            Seller seller = checkSeller.get();

            Optional<Order> checkOrder = orderRepository.findById(editOrderRequest.getOrder().getId());
            if (!checkOrder.isPresent()){
                throw new IllegalArgumentException("Đơn hàng không tồn tại");
            }
            Order order = checkOrder.get();

            Double oldDebt = customer.getDebt()==null ? 0 : customer.getDebt();
            Double newDebt = oldDebt - order.getTotalAmount() + editOrderRequest.getOrder().getTotalAmount();
            customer.setDebt(newDebt);

            order.setPositionCustomer(editOrderRequest.getOrder().getPositionCustomer());
            order.setPositionSeller(editOrderRequest.getOrder().getPositionSeller());
            order.setRepresentativeCustomer(editOrderRequest.getOrder().getRepresentativeCustomer());
            order.setRepresentativeSeller(editOrderRequest.getOrder().getRepresentativeSeller());
            order.setTax(editOrderRequest.getOrder().getTax());
            order.setTotalCost(editOrderRequest.getOrder().getTotalCost());
            order.setTotalAmount(editOrderRequest.getOrder().getTotalAmount());
            order.setContractCode(editOrderRequest.getOrder().getContractCode());
            order.setSigningDate(editOrderRequest.getOrder().getSigningDate());
            order.setCustomer(customer);
            order.setSeller(seller);
            order.setUpdateAt(DateTimeUtils.getCurrentDate());
            Double leftAmount = editOrderRequest.getOrder().getTotalAmount() - paymentService.CheckLeftAmount(editOrderRequest.getOrder().getId());
            order.setLeftAmount(leftAmount);
            Order newOrder = orderRepository.save(order);

            Map<Long, OrderDetail> currentOrderDetailsMap = new HashMap<>();
            List<OrderDetail> currentOrderDetails = orderDetailRepository.findAllByOrderId(order.getId());
            for (OrderDetail orderDetail : currentOrderDetails){
                currentOrderDetailsMap.put(orderDetail.getId(), orderDetail);
            }

            List<OrderDetailDto> orderDetailDtos = editOrderRequest.getOrderDetails();

            for (OrderDetailDto orderDetailDto : orderDetailDtos) {
                Optional<Product> checkProduct = productRepository.findById(orderDetailDto.getProductId());
                if (!checkProduct.isPresent()){
                    throw new IllegalArgumentException("Sản phẩm không tồn tại: " + orderDetailDto.getProductId());
                }
                Product product = checkProduct.get();

                OrderDetail existingOrderDetail = currentOrderDetailsMap.get(orderDetailDto.getOrderDetailId());
                if (existingOrderDetail != null){
                    existingOrderDetail.setPrice(orderDetailDto.getPrice());
                    existingOrderDetail.setMaterialWeight(orderDetailDto.getMaterialWeight());
                    existingOrderDetail.setProduct(product);
                    currentOrderDetailsMap.remove(orderDetailDto.getOrderDetailId());
                }else {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(newOrder);
                    orderDetail.setPrice(orderDetailDto.getPrice());
                    orderDetail.setMaterialWeight(orderDetailDto.getMaterialWeight());
                    orderDetail.setProduct(product);
                    currentOrderDetails.add(orderDetail);
                }
            }

            List<OrderDetail> orderDetailsToDelete = new ArrayList<>();

            for (OrderDetail orderDetailToDelete : currentOrderDetailsMap.values()){
                boolean toDelete = false;
                for (OrderDetailDto orderDetailDto : orderDetailDtos){
                    if (orderDetailDto.getOrderDetailId() != null && orderDetailDto.getOrderDetailId().equals(orderDetailToDelete.getId())){
                        toDelete = true;
                        break;
                    }
                }

                if (!toDelete){
                    orderDetailsToDelete.add(orderDetailToDelete);
                    currentOrderDetails.remove(orderDetailToDelete);
                }
            }

            orderDetailRepository.deleteAll(orderDetailsToDelete);
            orderDetailRepository.saveAll(currentOrderDetails);

            customerRepository.save(customer);

            return new PostOrderResponse(HttpStatus.OK.value(), "Cập nhật đơn hàng thành công");
        }catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public ResponseEntity getOrders(GetOrdersRequest getOrdersRequest) {
        PageRequest pageRequest = PageRequest.of(getOrdersRequest.getPageNumber(),
                getOrdersRequest.getPageSize());

        Page<Order> orders = orderRepository.findAllByIsDeletedFalse(pageRequest);
        if (!ObjectUtils.isEmpty(getOrdersRequest.getSearch())){
            String searchQuery = "%" + getOrdersRequest.getSearch() + "%";
            orders = orderRepository.findAllByOrderCodeLikeIgnoreCaseAndIsDeletedFalse(searchQuery, pageRequest);
        }

        return ResponseEntity.ok(orders);
    }

    @Override
    @Transactional
    public PostOrderResponse deleteOrder(Long id) {
        try {
            Optional<Order> checkOrder = orderRepository.findById(id);
            if (!checkOrder.isPresent()){
                throw new IllegalArgumentException("Đơn hàng không tồn tại");
            }
            Order order = checkOrder.get();
            order.setIsDeleted(true);
            orderRepository.save(order);

            return new PostOrderResponse(HttpStatus.OK.value(), "Xóa đơn hàng thành công");
        }catch (IllegalArgumentException ex){
            throw ex;
        }
    }

    @Override
    public PostOrderResponse setIsPaymented(SetIsPaymentedRequest setIsPaymentedRequest) {
        try {
            Optional<Order> checkOrder = orderRepository.findById(setIsPaymentedRequest.getId());
            if (!checkOrder.isPresent()){
                throw new IllegalArgumentException("Đơn hàng không tồn tại");
            }
            Order order = checkOrder.get();
            order.setIsPaymented(setIsPaymentedRequest.getPayment());
            orderRepository.save(order);

            return new PostOrderResponse(HttpStatus.OK.value(), "Đổi trạng thái thành công");
        }catch (IllegalArgumentException ex){
            throw ex;
        }
    }

    private Long getLatesId() {
        Order lastId = orderRepository.findFirstByOrderByIdDesc();
        return lastId != null ? lastId.getId() : null;
    }
    @Override
    public OrderDto convertToOrderDto(Order order){
        return modelMapper.map(order,OrderDto.class);
    }
    @Override
    public OrderResponse convertToOrderResponse(Order order){
        return modelMapper.map(order,OrderResponse.class);
    }
}
