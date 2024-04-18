package com.app.ConStructCompany.Response;

import lombok.Data;

import java.util.List;

@Data
public class GetStatisticResponse {
    private StatisticResponse statistic;
    private List<StatisticDetailResponse> statisticDetails;
}
