package com.app.ConStructCompany.Response;

import com.app.ConStructCompany.Entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
@Data
@AllArgsConstructor
public class GetCustomersResponse {
    private Page<Customer> customerPage;

}
