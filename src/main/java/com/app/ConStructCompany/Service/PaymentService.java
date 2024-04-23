package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Order;
import com.app.ConStructCompany.Entity.Payment;
import com.app.ConStructCompany.Repository.OrderRepository;
import com.app.ConStructCompany.Repository.PaymentRepository;
import com.app.ConStructCompany.Request.RequestPayment;
import com.app.ConStructCompany.Request.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    public final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    public List<Payment> getPaymentByOrderId(Long id){
        return paymentRepository.findAllByOrderId(id);
    }
    public List<PaymentDTO> getAllPaymentDTO(Long id){
        List<Payment> paymentList = paymentRepository.findAllByOrderId(id);
        return paymentList.stream().map(this::convertToPaymentDTO).collect(Collectors.toList());
    }

    public void addPayment(RequestPayment requestPayment){
        List<Payment> paymentList = new ArrayList<>();
        List<PaymentDTO> listDTO= requestPayment.getPayments();
        try {
            Optional<Order> orderOptional = orderRepository.findById(requestPayment.getOrderId());
            if (orderOptional.isEmpty()){
                throw new IllegalArgumentException("Đơn hàng không tồn tại");
            }
            Order order = orderOptional.get();
            //check exists
            List<Payment> paymentCheckExistList = getPaymentByOrderId(order.getId());
            Set<Long> idCheck = new HashSet<>();
            for (PaymentDTO paymentDTO : listDTO){
                Payment payment = new Payment();
                if (paymentDTO.getId()!=null){
                    idCheck.add(paymentDTO.getId());
                    payment.setId(paymentDTO.getId());
                }
                payment.setDay(paymentDTO.getDay());
                payment.setPrice(paymentDTO.getPrice());
                payment.setOrder(order);
                payment.setCreateAt(new Date());
                paymentList.add(payment);
            }
            for (Payment payment : paymentCheckExistList){
                if (!idCheck.contains(payment.getId())){
                    paymentRepository.deleteById(payment.getId());
                }
            }
//            System.out.println(paymentList);
            paymentRepository.saveAll(paymentList);
            //update order
            double left = order.getTotalAmount()-CheckLeftAmount(order.getId());
            order.setIsPaymented(false);
            if (left==0.0){
                order.setIsPaymented(true);
            }
            order.setLeftAmount(left);
            orderRepository.save(order);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    public Double CheckLeftAmount(Long id){
        List<Payment> paymentList = getPaymentByOrderId(id);
        Double total = paymentList.stream()
                .mapToDouble(Payment::getPrice) // Lấy giá trị của getPrice từ mỗi phần tử
                .sum();
        System.out.println(total);

        return total;
    }
    public PaymentDTO convertToPaymentDTO(Payment payment){
        return modelMapper.map(payment,PaymentDTO.class);
    }
}
