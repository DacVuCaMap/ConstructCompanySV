package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Statistic;
import com.app.ConStructCompany.Entity.StatisticDetail;
import com.app.ConStructCompany.Repository.StatisticDetailRepository;
import com.app.ConStructCompany.Repository.StatisticRepository;
import com.app.ConStructCompany.Request.StatisticDetailRequest;
import com.app.ConStructCompany.Response.StatisticDetailResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class StatisticDetailService {
    private final StatisticDetailRepository statisticDetailRepository;
    private final StatisticRepository statisticRepository;
    private final ModelMapper modelMapper;

    public Page<StatisticDetailResponse> getStatisticDetail(Pageable pageable){
        Page<StatisticDetail> statisticDetailPage = statisticDetailRepository.findAll(pageable);
        return statisticDetailPage.map(statisticDetail -> {
            StatisticDetailResponse response = modelMapper.map(statisticDetail, StatisticDetailResponse.class);
            response.setStatisticID(statisticDetail.getId());
            return response;
        });
    }
    public void addStatisticDetail(StatisticDetailRequest statisticDetailRequest,Statistic statistic){
        StatisticDetail statisticDetail =new StatisticDetail();
        statisticDetail.setStatistic(statistic);
        statisticDetail.setPrice(statisticDetailRequest.getPrice());
        statisticDetail.setDay(statisticDetailRequest.getDay());
        statisticDetail.setMaterialWeight(statisticDetailRequest.getMaterialWeight());
        statisticDetail.setTotalAmount(statisticDetailRequest.getTotalAmount());
        statisticDetail.setTicket(statisticDetailRequest.getTicket());
        statisticDetail.setTrailer(statisticDetailRequest.getTrailer());
        statisticDetail.setLicensePlate(statisticDetailRequest.getLicensePlate());
        statisticDetail.setTypeProduct(statisticDetailRequest.getTypeProduct());
        statisticDetail.setNote(statisticDetailRequest.getNote());
        statisticDetail.setUnit(statisticDetailRequest.getUnit());
        statisticDetail.setProId(statisticDetailRequest.getProId());
        statisticDetailRepository.save(statisticDetail);
    }
}
