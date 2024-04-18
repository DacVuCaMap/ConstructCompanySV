package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Request.AddCustomerRequest;
import com.app.ConStructCompany.Request.EditCustomerRequest;
import com.app.ConStructCompany.Request.GetCustomersRequest;
import com.app.ConStructCompany.Response.GetCustomersResponse;
import com.app.ConStructCompany.Response.PostCustomerResponse;
import com.app.ConStructCompany.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/add-customer")
    public PostCustomerResponse addCustomer(@RequestBody @Valid AddCustomerRequest addCustomerRequest){
        return customerService.addCustomer(addCustomerRequest);
    }

    @PostMapping("/edit-customer")
    public PostCustomerResponse editCustomer(@RequestBody @Valid EditCustomerRequest editCustomerRequest){
        return customerService.editCustomer(editCustomerRequest);
    }

    @PostMapping("/delete-customer")
    public PostCustomerResponse deleteCustomer(@RequestParam Long id){
        return customerService.deleteCustomer(id);
    }

    @GetMapping("/get")
    public GetCustomersResponse getCustomers(GetCustomersRequest getCustomersRequest){
        return new GetCustomersResponse(customerService.getCustomers(getCustomersRequest));
    }
}
