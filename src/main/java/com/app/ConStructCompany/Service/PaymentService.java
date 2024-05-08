package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Order;
import com.app.ConStructCompany.Entity.Payment;
import com.app.ConStructCompany.Entity.Statistic;
import com.app.ConStructCompany.Repository.OrderRepository;
import com.app.ConStructCompany.Repository.PaymentRepository;
import com.app.ConStructCompany.Repository.StatisticRepository;
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
    private final StatisticRepository statisticRepository;
    private final ModelMapper modelMapper;
    public List<PaymentDTO> getAllPaymentDTO(Long id){
        List<Payment> paymentList = paymentRepository.findAllByStatisticId(id);
        return paymentList.stream().map(this::convertToPaymentDTO).collect(Collectors.toList());
    }
    private List<Payment> getAllByStatisticId(Long id){
        return paymentRepository.findAllByStatisticId(id);
    }
    public void addPayment(RequestPayment requestPayment){
        List<Payment> paymentList = new ArrayList<>();
        List<PaymentDTO> listDTO= requestPayment.getPayments();
        try {
            Optional<Statistic> statisticOptional = statisticRepository.findById(requestPayment.getStatisticId());
            if (statisticOptional.isEmpty()){
                throw new IllegalArgumentException("Đơn hàng không tồn tại");
            }
            Statistic statistic = statisticOptional.get();
            //check exists
            List<Payment> paymentCheckExistList = getAllByStatisticId(statistic.getId());
            Set<Long> idCheck = new HashSet<>();
            for (PaymentDTO paymentDTO : listDTO){
                if (!paymentDTO.getDay().before(statistic.getStartDay()) || !paymentDTO.getDay().after(statistic.getEndDay())){
                    Payment payment = new Payment();
                    if (paymentDTO.getId()!=null){
                        idCheck.add(paymentDTO.getId());
                        payment.setId(paymentDTO.getId());
                    }
                    payment.setDay(paymentDTO.getDay());
                    payment.setPrice(paymentDTO.getPrice());
                    payment.setStatistic(statistic);
                    payment.setCreateAt(new Date());
                    payment.setDescription(paymentDTO.getDescription());
                    paymentList.add(payment);
                }

            }
            for (Payment payment : paymentCheckExistList){
                if (!idCheck.contains(payment.getId())){
                    paymentRepository.deleteById(payment.getId());
                }
            }
            paymentRepository.saveAll(paymentList);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    public void addOnePayment(PaymentDTO paymentDTO){
        Date day = paymentDTO.getDay();
        Payment payment = new Payment();
        payment.setDay(paymentDTO.getDay());
        payment.setPrice(paymentDTO.getPrice());
        payment.setCreateAt(new Date());
        payment.setDescription(paymentDTO.getDescription());
        List<Statistic> statisticList = statisticRepository.findAllByOrderIdAndIsDeletedFalseOrderByCreateAtAsc(paymentDTO.getOrderId());
        for (Statistic statistic : statisticList){
            if (day.before(statistic.getEndDay()) && day.after(statistic.getStartDay())){
                payment.setStatistic(statistic);
                paymentRepository.save(payment);
            }

        }
    }
//    public Double CheckLeftAmount(Long id){
//        List<Payment> paymentList = getPaymentByOrderId(id);
//        Double total = paymentList.stream()
//                .mapToDouble(Payment::getPrice) // Lấy giá trị của getPrice từ mỗi phần tử
//                .sum();
//        System.out.println(total);
//
//        return total;
//    }
    public PaymentDTO convertToPaymentDTO(Payment payment){
        return modelMapper.map(payment,PaymentDTO.class);
    }
}
