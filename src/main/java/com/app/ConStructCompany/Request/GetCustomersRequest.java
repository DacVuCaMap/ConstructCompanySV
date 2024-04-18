package com.app.ConStructCompany.Request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GetCustomersRequest extends PageRequest{
    private String search;
    private PageRequest pageRequest;
    private String filter;

    protected GetCustomersRequest(int page, int size, String search, String filter) {
        super(page, size, Sort.unsorted());
        this.search = search;
        this.filter = filter;
    }
}
