package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.*;
import com.app.ConStructCompany.Repository.CustomerRepository;
import com.app.ConStructCompany.Repository.SellerRepository;
import com.app.ConStructCompany.Repository.StatisticDetailRepository;
import com.app.ConStructCompany.Repository.StatisticRepository;
import com.app.ConStructCompany.Request.StatisticAddRequest;
import com.app.ConStructCompany.Request.StatisticDetailRequest;
import com.app.ConStructCompany.Request.StatisticRequest;
import com.app.ConStructCompany.Request.dto.OrderDetailDto;
import com.app.ConStructCompany.Request.dto.StatisticDTO;
import com.app.ConStructCompany.Response.CustomerResponse;
import com.app.ConStructCompany.Response.GetStatisticResponse;
import com.app.ConStructCompany.Response.StatisticDetailResponse;
import com.app.ConStructCompany.Response.StatisticResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    private final StatisticDetailService statisticDetailService;
    private final StatisticDetailRepository statisticDetailRepository;

    public Page<StatisticDTO> findAll(Pageable pageable) {
        Page<Statistic> statisticPage = statisticRepository.findAllByIsDeletedFalse(pageable);
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
        Statistic statistic = new Statistic();
        StatisticRequest statisticRequest = statisticAddRequest.getStatistic();
        List<StatisticDetailRequest> statisticDetailRequests = statisticAddRequest.getStatisticDetails();
        Optional<Customer> OptionCustomer = customerRepository.findById(statisticRequest.getCustomerId());
        Optional<Seller> OptionalSeller = sellerRepository.findById(statisticRequest.getSellerId());
        if (OptionCustomer.isPresent() && OptionalSeller.isPresent()) {
            Customer customer = OptionCustomer.get();
            Seller seller = OptionalSeller.get();
            statistic.setCustomer(customer);
            statistic.setSeller(seller);
            statistic.setRepresentativeCustomer(statisticRequest.getRepresentativeCustomer());
            statistic.setPositionCustomer(statisticRequest.getPositionCustomer());
            statistic.setRepresentativeSeller(statisticRequest.getRepresentativeSeller());
            statistic.setPositionSeller(statisticRequest.getPositionSeller());
            statistic.setTotalAmount(statisticRequest.getTotalAmount());
            statistic.setCreateAt(new Date());
            statistic.setIsDeleted(false);
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
            statisticDetailRepository.saveAll(currentStatisticDetails);

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
}
