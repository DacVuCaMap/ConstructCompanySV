package com.app.ConStructCompany.Response;

import com.app.ConStructCompany.Request.dto.QLCNDto;
import lombok.Data;

import java.util.List;

@Data
public class QLCNDtoResponse {
    private List<QLCNDto> content;
    private int totalPages;
}
