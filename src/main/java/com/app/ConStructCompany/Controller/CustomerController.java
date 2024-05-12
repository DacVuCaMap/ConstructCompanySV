package com.app.ConStructCompany.Controller;

import com.app.ConStructCompany.Entity.Customer;
import com.app.ConStructCompany.Repository.CustomerRepository;
import com.app.ConStructCompany.Request.AddCustomerRequest;
import com.app.ConStructCompany.Request.EditCustomerRequest;
import com.app.ConStructCompany.Request.GetCustomersRequest;
import com.app.ConStructCompany.Request.dto.QLCNDto;
import com.app.ConStructCompany.Response.GetCustomersResponse;
import com.app.ConStructCompany.Response.PostCustomerResponse;
import com.app.ConStructCompany.Response.QLCNDtoResponse;
import com.app.ConStructCompany.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    @PostMapping("/add-customer")
    public PostCustomerResponse addCustomer(@RequestBody AddCustomerRequest addCustomerRequest){
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
    @GetMapping("/get-cus")
    public ResponseEntity<?> getCustomersResponse(GetCustomersRequest getCustomersRequest){
        QLCNDtoResponse customerPage = customerService.getCustomersByCountOrder(getCustomersRequest);
        return ResponseEntity.ok().body(customerPage);
    }
    @GetMapping("/get-details/{id}")
    public ResponseEntity<?> getCustomerDetails(@PathVariable Long id){
        return ResponseEntity.ok().body(customerRepository.findById(id));
    }
}
