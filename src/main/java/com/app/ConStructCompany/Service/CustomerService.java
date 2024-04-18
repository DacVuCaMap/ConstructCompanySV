package com.app.ConStructCompany.Service;

import com.app.ConStructCompany.Entity.Customer;
import com.app.ConStructCompany.Request.AddCustomerRequest;
import com.app.ConStructCompany.Request.EditCustomerRequest;
import com.app.ConStructCompany.Request.GetCustomersRequest;
import com.app.ConStructCompany.Response.PostCustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    PostCustomerResponse addCustomer(AddCustomerRequest addCustomerRequest);
    PostCustomerResponse editCustomer(EditCustomerRequest editCustomerRequest);
    PostCustomerResponse deleteCustomer(Long id);
    Page<Customer> getCustomers(GetCustomersRequest getCustomersRequest);
}