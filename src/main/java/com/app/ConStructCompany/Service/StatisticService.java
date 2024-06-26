package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.*;
import com.app.ConStructCompany.Repository.*;
import com.app.ConStructCompany.Request.StatisticAddRequest;
import com.app.ConStructCompany.Request.StatisticDetailRequest;
import com.app.ConStructCompany.Request.StatisticRequest;
import com.app.ConStructCompany.Request.dto.OrderDetailDto;
import com.app.ConStructCompany.Request.dto.OrderDto;
import com.app.ConStructCompany.Request.dto.StatisticDTO;
import com.app.ConStructCompany.Response.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
@AllArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final OrderRepository orderRepository;
    private final StatisticDetailService statisticDetailService;
    private final StatisticDetailRepository statisticDetailRepository;
    private final ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;
    public Page<StatisticDTO> findAll(Pageable pageable,String search) {
        System.out.println(search);
        Page<Statistic> statisticPage = statisticRepository.findAll(pageable);
        if (search!=null){
            statisticPage = statisticRepository.findAllByCustomerCompanyNameContainingIgnoreCaseAndIsDeletedFalse(search,pageable);
        }
        return statisticPage.map(this::convertToStatisticDTO);
    }

    private StatisticDTO convertToStatisticDTO(Statistic statistic) {
        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setId(statistic.getId());
        statisticDTO.setCompanyName(statistic.getCustomer().getCompanyName());
        statisticDTO.setCreateAt(statistic.getCreateAt());
        statisticDTO.setUpdateAt(statistic.getUpdateAt());
        statisticDTO.setTotalAmount(statistic.getTotalAmount());
        return statisticDTO;
    }


    public Statistic addStatistic(StatisticAddRequest statisticAddRequest) {
        //check day
//        if (!checkDayIn(statisticAddRequest.getStatistic().getStartDay(), statisticAddRequest.getStatistic().getOrderId())
//        || !checkDayIn(statisticAddRequest.getStatistic().getEndDay(), statisticAddRequest.getStatistic().getOrderId())){
//            return null;
//        }
//        System.out.println("vao day");
        Statistic statistic = new Statistic();
        StatisticRequest statisticRequest = statisticAddRequest.getStatistic();
        List<StatisticDetailRequest> statisticDetailRequests = statisticAddRequest.getStatisticDetails();
        Optional<Customer> OptionCustomer = customerRepository.findById(statisticRequest.getCustomerId());
        Optional<Seller> OptionalSeller = sellerRepository.findById(statisticRequest.getSellerId());
        Optional<Order> OptionalOrder = orderRepository.findById(statisticRequest.getOrderId());
        if (OptionCustomer.isPresent() && OptionalSeller.isPresent() && OptionalOrder.isPresent()) {
            Customer customer = OptionCustomer.get();
            Seller seller = OptionalSeller.get();
            Order order = OptionalOrder.get();
            statistic.setOrder(order);
            statistic.setCustomer(customer);
            statistic.setSeller(seller);
            statistic.setRepresentativeCustomer(statisticRequest.getRepresentativeCustomer());
            statistic.setPositionCustomer(statisticRequest.getPositionCustomer());
            statistic.setRepresentativeSeller(statisticRequest.getRepresentativeSeller());
            statistic.setPositionSeller(statisticRequest.getPositionSeller());
            statistic.setTotalAmount(statisticRequest.getTotalAmount());
            statistic.setEndDay(statisticRequest.getEndDay());
            statistic.setStartDay(statisticRequest.getStartDay());
            statistic.setCreateAt(new Date());
            statistic.setIsDeleted(false);
            statistic.setTotalPay(0.0);
            //set cashleft
            //khong can
            //save
            Statistic statisticSave = statisticRepository.save(statistic);
            for (StatisticDetailRequest statisticDetailRequest : statisticDetailRequests) {
                statisticDetailService.addStatisticDetail(statisticDetailRequest, statisticSave);
            }
            return statisticSave;
        } else {
            return null;
        }
    }

    @Transactional
    public ResponseEntity editStatistic(StatisticAddRequest statisticAddRequest) {
        try {
            StatisticRequest statisticRequest = statisticAddRequest.getStatistic();
            List<StatisticDetailRequest> statisticDetailRequests = statisticAddRequest.getStatisticDetails();

            Optional<Customer> optionCustomer = customerRepository.findById(statisticRequest.getCustomerId());
            if (!optionCustomer.isPresent()) {
                throw new IllegalArgumentException("Không tồn tại khách hàng");
            }

            Optional<Seller> optionalSeller = sellerRepository.findById(statisticRequest.getSellerId());
            if (!optionalSeller.isPresent()) {
                throw new IllegalArgumentException("Không tồn tại người bán");
            }
            Customer customer = optionCustomer.get();
            Seller seller = optionalSeller.get();

            Optional<Statistic> checkStatistic = statisticRepository.findById(statisticRequest.getId());
            if (!checkStatistic.isPresent()) {
                throw new IllegalArgumentException("Không tồn tại bảng thống kê");
            }

            Statistic statistic = checkStatistic.get();

            statistic.setCustomer(customer);
            statistic.setSeller(seller);
            statistic.setRepresentativeCustomer(statisticRequest.getRepresentativeCustomer());
            statistic.setPositionCustomer(statisticRequest.getPositionCustomer());
            statistic.setRepresentativeSeller(statisticRequest.getRepresentativeSeller());
            statistic.setPositionSeller(statisticRequest.getPositionSeller());
            statistic.setTotalAmount(statisticRequest.getTotalAmount());
            statistic.setUpdateAt(new Date());
            statistic.setEndDay(statisticRequest.getEndDay());
            statistic.setStartDay(statisticRequest.getStartDay());

            Statistic statisticSave = statisticRepository.save(statistic);

            Map<Long, StatisticDetail> currentStatisticDetailsMap = new HashMap<>();
            List<StatisticDetail> currentStatisticDetails = statisticDetailRepository.findAllByStatisticId(statisticRequest.getId());
            for (StatisticDetail statisticDetail : currentStatisticDetails) {
                currentStatisticDetailsMap.put(statisticDetail.getId(), statisticDetail);
            }

            for (StatisticDetailRequest statisticDetailRequest : statisticDetailRequests) {
                StatisticDetail existingStatisticDetail = currentStatisticDetailsMap.get(statisticDetailRequest.getStatisticDetailId());
                if (existingStatisticDetail != null) {
                    existingStatisticDetail.setPrice(statisticDetailRequest.getPrice());
                    existingStatisticDetail.setDay(statisticDetailRequest.getDay());
                    existingStatisticDetail.setMaterialWeight(statisticDetailRequest.getMaterialWeight());
                    existingStatisticDetail.setTotalAmount(statisticDetailRequest.getTotalAmount());
                    existingStatisticDetail.setTicket(statisticDetailRequest.getTicket());
                    existingStatisticDetail.setTrailer(statisticDetailRequest.getTrailer());
                    existingStatisticDetail.setLicensePlate(statisticDetailRequest.getLicensePlate());
                    existingStatisticDetail.setTypeProduct(statisticDetailRequest.getTypeProduct());
                    existingStatisticDetail.setNote(statisticDetailRequest.getNote());
                    existingStatisticDetail.setProId(statisticDetailRequest.getProId());
                    currentStatisticDetailsMap.remove(statisticDetailRequest.getStatisticDetailId());
                } else {
                    StatisticDetail statisticDetail = new StatisticDetail();
                    statisticDetail.setStatistic(statisticSave);
                    statisticDetail.setPrice(statisticDetailRequest.getPrice());
                    statisticDetail.setDay(statisticDetailRequest.getDay());
                    statisticDetail.setMaterialWeight(statisticDetailRequest.getMaterialWeight());
                    statisticDetail.setTotalAmount(statisticDetailRequest.getTotalAmount());
                    statisticDetail.setTicket(statisticDetailRequest.getTicket());
                    statisticDetail.setTrailer(statisticDetailRequest.getTrailer());
                    statisticDetail.setLicensePlate(statisticDetailRequest.getLicensePlate());
                    statisticDetail.setTypeProduct(statisticDetailRequest.getTypeProduct());
                    statisticDetail.setNote(statisticDetailRequest.getNote());
                    statisticDetail.setProId(statisticDetailRequest.getProId());
                    currentStatisticDetails.add(statisticDetail);
                }
            }

            List<StatisticDetail> statisticDetailsToDelete = new ArrayList<>();
            for (StatisticDetail statisticDetailToDelete : currentStatisticDetailsMap.values()) {
                if (!statisticDetailRequests.stream().anyMatch(req -> req.getStatisticDetailId() != null && req.getStatisticDetailId().equals(statisticDetailToDelete.getId()))) {
                    statisticDetailsToDelete.add(statisticDetailToDelete);
                    currentStatisticDetails.remove(statisticDetailToDelete);
                }
            }

            statisticDetailRepository.deleteAll(statisticDetailsToDelete);
            System.out.println(currentStatisticDetails);
            statisticDetailRepository.saveAll(currentStatisticDetails);
            updateAllStatisticByOrder(statisticAddRequest.getStatistic().getOrderId());
            return ResponseEntity.ok("Cập nhật thành công");
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public ResponseEntity<?> deleteStatistic(Long id) {
        Optional<Statistic> checkStatistic = statisticRepository.findById(id);
        if (!checkStatistic.isPresent()) {
            throw new IllegalArgumentException("Không tồn tại bảng thống kê");
        }

        Statistic statistic = checkStatistic.get();
        statistic.setIsDeleted(true);
        statisticRepository.save(statistic);

        return ResponseEntity.ok("Xóa thành công");
    }
    public ResponseEntity<?> getDetailsStatistic(Long id){
        Optional<Statistic> checkStatistic = statisticRepository.findById(id);
        if (checkStatistic.isEmpty() || checkStatistic.get().getIsDeleted()){
            return ResponseEntity.badRequest().body("Khong ton tai");
        }
        Statistic statistic = checkStatistic.get();
        Customer customer = statistic.getCustomer();
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setCompanyName(customer.getCompanyName());
        customerResponse.setAddress(customer.getAddress());
        customerResponse.setTaxCode(customer.getTaxCode());
        customerResponse.setDebt(customer.getDebt());
        customerResponse.setCreateAt(customer.getCreateAt());
        customerResponse.setUpdateAt(customer.getUpdateAt());
        customerResponse.setTotalPayment(customer.getTotalPayment());
        customerResponse.setPhoneNumber(customer.getPhoneNumber());
        customerResponse.setPositionCustomer(customer.getPositionCustomer());
        customerResponse.setRepresentativeCustomer(customer.getRepresentativeCustomer());
        customerResponse.setEmail(customer.getEmail());

        //order
        Order order = statistic.getOrder();
        OrderDto orderDto = order!=null ? modelMapper.map(order, OrderDto.class) : null;

        StatisticResponse statisticResponse = new StatisticResponse();
        statisticResponse.setId(statistic.getId());
        statisticResponse.setCustomer(customerResponse);
        statisticResponse.setSellerId(statistic.getSeller().getId());
        statisticResponse.setRepresentativeCustomer(statistic.getRepresentativeCustomer());
        statisticResponse.setPositionCustomer(statistic.getPositionCustomer());
        statisticResponse.setRepresentativeSeller(statistic.getRepresentativeSeller());
        statisticResponse.setPositionSeller(statistic.getPositionSeller());
        statisticResponse.setTotalAmount(statistic.getTotalAmount());
        statisticResponse.setCreateAt(statistic.getCreateAt());
        statisticResponse.setUpdateAt(statistic.getUpdateAt());
        statisticResponse.setEndDay(statistic.getEndDay());
        statisticResponse.setStartDay(statistic.getStartDay());
        statisticResponse.setOrder(orderDto);
        List<StatisticDetail> getStatisticDetails = statisticDetailRepository.findAllByStatisticId(statistic.getId());
        List<StatisticDetailResponse> responseList = new ArrayList<>();
        for (StatisticDetail statisticDetail : getStatisticDetails){
            StatisticDetailResponse temp = new StatisticDetailResponse();
            temp.setId(statisticDetail.getId());
            temp.setStatisticID(statistic.getId());
            temp.setDay(statisticDetail.getDay());
            temp.setLicensePlate(statisticDetail.getLicensePlate());
            temp.setTrailer(statisticDetail.getTrailer());
            temp.setTicket(statisticDetail.getTicket());
            temp.setTypeProduct(statisticDetail.getTypeProduct());
            temp.setMaterialWeight(statisticDetail.getMaterialWeight());
            temp.setPrice(statisticDetail.getPrice());
            temp.setTotalAmount(statisticDetail.getTotalAmount());
            temp.setNote(statisticDetail.getNote());
            temp.setUnit(statisticDetail.getUnit());
            temp.setProId(statisticDetail.getProId());
            responseList.add(temp);
        }
        GetStatisticResponse getStatisticResponse = new GetStatisticResponse();
        getStatisticResponse.setStatistic(statisticResponse);
        getStatisticResponse.setStatisticDetails(responseList);
        return ResponseEntity.ok().body(getStatisticResponse);

    }
//    public List<StatisticDTO> getStatisticByOrder(Long id){
//        List<Statistic> statistics = statisticRepository.findAllByOrderIdAndIsDeletedFalseOrderByCreateAtAsc(id);
//        List<StatisticDTO> statisticDTOS = new ArrayList<>();
//        for (Statistic statistic : statistics){
//            StatisticDTO statisticDTO = modelMapper.map(statistic, StatisticDTO.class);
//            System.out.println(statisticDTO);
//            List<Payment> payments = paymentRepository.findAllByStatisticId(statistic.getId());
//            System.out.println("paylist: "+payments);
//            statisticDTO.setPayments(payments);
//            statisticDTOS.add(statisticDTO);
//        }
//        System.out.println(statisticDTOS);
//        return statisticDTOS;
//    }
    public int countStatistic(){
        return statisticRepository.countByIsDeletedFalse();
    }
//    private boolean checkDayIn(Date day,Long orderId){
//        List<StatisticDTO> list = getStatisticByOrder(orderId);
//        System.out.println("checkDayin" + list);
//        for (StatisticDTO statisticDTO : list){
//            if (!day.before(statisticDTO.getStartDay()) && !day.after(statisticDTO.getEndDay())){
//                return false;
//            }
//        }
//        return true;
//    }
    public StatisticResponse convertStatisticResponse(Statistic statistic){
        return modelMapper.map(statistic,StatisticResponse.class);
    }
    public void updateAllStatisticByOrder(Long orderId){
        List<Statistic> statistics = statisticRepository.findAllByOrderIdAndIsDeletedFalseOrderByCreateAtAsc(orderId);
        List<Statistic> statisticNew = new ArrayList<>();
        double cashLeft = 0.0;
        double totalPayment = 0.0;
        for (Statistic statistic : statistics){
            statistic.setCashLeft(cashLeft);
            List<Payment> payments = paymentRepository.findAllByStatisticId(statistic.getId());
            Double sumPay = payments.stream().mapToDouble(Payment::getPrice).sum();
            //get day
            Optional<Date> maxDate = payments.stream()
                    .map(Payment::getDay)
                    .max(Comparator.comparing(Date::getTime));

            Optional<Date> minDate = payments.stream()
                    .map(Payment::getDay)
                    .min(Comparator.comparing(Date::getTime));

            if (maxDate.isPresent() && minDate.isPresent()) {
                Date maxPaymentDate = maxDate.get();
                Date minPaymentDate = minDate.get();
                statistic.setStartDay(minPaymentDate);
                statistic.setEndDay(maxPaymentDate);

            }
            cashLeft = statistic.getCashLeft()+sumPay-statistic.getTotalAmount();
            totalPayment +=sumPay;
            statistic.setTotalPay(sumPay);
            statisticNew.add(statistic);
        }
        statisticRepository.saveAll(statisticNew);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            double leftAmount = order.getTotalAmount()-totalPayment;
            order.setLeftAmount(leftAmount);
            order.setIsPaymented(leftAmount <= 0.0);
            orderRepository.save(order);
            //update debt customer
            Customer customer = order.getCustomer();
            Double debt = totDebtCus(customer.getId());
            Double pay = totPayCus(customer.getId());
            customer.setPayDebt(pay);
            customer.setTotalDebt(debt);
            customerRepository.save(customer);
        }
    }
    public Double totDebtCus(Long cusId){
        List<Order> orderList = orderRepository.findAllByCustomerIdAndIsDeletedFalseAndIsPaymentedFalseOrderByCreateAtAsc(cusId);
        return orderList.stream().mapToDouble(Order::getTotalAmount).sum();
    }
    public Double totPayCus(Long cusId){
        List<Order> orderList = orderRepository.findAllByCustomerIdAndIsDeletedFalseAndIsPaymentedFalseOrderByCreateAtAsc(cusId);
        return orderList.stream()
                .mapToDouble(Order::getLeftAmount)
                .sum();
    }

}
