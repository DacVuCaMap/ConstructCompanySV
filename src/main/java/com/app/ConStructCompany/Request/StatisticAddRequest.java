package com.app.ConStructCompany.Request;

import lombok.Data;

import java.util.List;

@Data
public class StatisticAddRequest {
    private StatisticRequest statistic;
    private List<StatisticDetailRequest> statisticDetails;
}
