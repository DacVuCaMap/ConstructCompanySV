package com.app.ConStructCompany.Service.impl;

import com.app.ConStructCompany.Entity.Customer;
import com.app.ConStructCompany.Entity.Order;
import com.app.ConStructCompany.Repository.CustomerRepository;
import com.app.ConStructCompany.Repository.OrderRepository;
import com.app.ConStructCompany.Request.*;
import com.app.ConStructCompany.Request.dto.QLCNDto;
import com.app.ConStructCompany.Response.PostCustomerResponse;
import com.app.ConStructCompany.Response.QLCNDtoResponse;
import com.app.ConStructCompany.Service.CustomerService;
import com.app.ConStructCompany.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    @Override
    public PostCustomerResponse addCustomer(AddCustomerRequest addCustomerRequest) {

        if (!addCustomerRequest.isValidRequest()){
            return new PostCustomerResponse(HttpStatus.BAD_REQUEST.value(), "Thông tin nhập vào là bắt buộc");
        }

        if (!addCustomerRequest.isValidTaxCode()){
            return new PostCustomerResponse(HttpStatus.BAD_REQUEST.value(), "Mã số thuế phải là chuỗi số");
        }

        Customer checkTaxCode = customerRepository.findByTaxCode(addCustomerRequest.getTaxCode());
        if (!ObjectUtils.isEmpty(checkTaxCode)){
            return new PostCustomerResponse(HttpStatus.BAD_REQUEST.value(), "Mã số thuế đã bị trùng");
        }

        Customer checkPhoneNumber = customerRepository.findByPhoneNumber(addCustomerRequest.getPhoneNumber());
        if (!ObjectUtils.isEmpty(checkPhoneNumber)){
            return new PostCustomerResponse(HttpStatus.BAD_REQUEST.value(), "Số điện thoại bị trùng");
        }

        Customer customer = new Customer();
        customer.setAddress(addCustomerRequest.getAddress());
        customer.setCompanyName(addCustomerRequest.getCompanyName());
        customer.setTaxCode(addCustomerRequest.getTaxCode());
        customer.setCreateAt(DateTimeUtils.getCurrentDate());
        customer.setPositionCustomer(addCustomerRequest.getPositionCustomer());
        customer.setRepresentativeCustomer(addCustomerRequest.getRepresentativeCustomer());
        customer.setIsDeleted(false);
        customer.setPhoneNumber(addCustomerRequest.getPhoneNumber());
        customer.setEmail(addCustomerRequest.getEmail());
        customerRepository.save(customer);

        return new PostCustomerResponse(HttpStatus.OK.value(), "Tạo khách hàng mới thành công");
    }

    @Override
    public PostCustomerResponse editCustomer(EditCustomerRequest editCustomerRequest) {
        if (!editCustomerRequest.isValidRequest()){
            return new PostCustomerResponse(HttpStatus.BAD_REQUEST.value(), "Thông tin nhập vào là bắt buộc");
        }

        if (!editCustomerRequest.isValidTaxCode()){
            return new PostCustomerResponse(HttpStatus.BAD_REQUEST.value(), "Mã số thuế phải là chuỗi số");
        }

        Optional<Customer> checkCustomer = customerRepository.findById(editCustomerRequest.getId());
        if (!checkCustomer.isPresent()){
            return new PostCustomerResponse(HttpStatus.BAD_REQUEST.value(), "Không tồn tại khách hàng");
        }
        Customer customer = checkCustomer.get();

        if (!customer.getTaxCode().equals(editCustomerRequest.getTaxCode())){
            Customer checkTaxCode = customerRepository.findByTaxCode(editCustomerRequest.getTaxCode());
            if (!ObjectUtils.isEmpty(checkTaxCode)){
                return new PostCustomerResponse(HttpStatus.BAD_REQUEST.value(), "Mã số thuế đã bị trùng");
            }
        }

        if (!customer.getPhoneNumber().equals(editCustomerRequest.getPhoneNumber())){
            Customer checkPhoneNumber = customerRepository.findByPhoneNumber(editCustomerRequest.getPhoneNumber());
            if (!ObjectUtils.isEmpty(checkPhoneNumber)){
                return new PostCustomerResponse(HttpStatus.BAD_REQUEST.value(), "Số điện thoại bị trùng");
            }
        }

        customer.setTaxCode(editCustomerRequest.getTaxCode());
        customer.setAddress(editCustomerRequest.getAddress());
        customer.setCompanyName(editCustomerRequest.getCompanyName());
        customer.setUpdateAt(DateTimeUtils.getCurrentDate());
        customer.setPhoneNumber(editCustomerRequest.getPhoneNumber());
        customer.setPositionCustomer(editCustomerRequest.getPositionCustomer());
        customer.setRepresentativeCustomer(editCustomerRequest.getRepresentativeCustomer());
        customer.setEmail(editCustomerRequest.getEmail());
        customerRepository.save(customer);
        return new PostCustomerResponse(HttpStatus.OK.value(), "Thay đổi thông tin khách hàng thành công");
    }

    @Override
    public PostCustomerResponse deleteCustomer(Long id) {
        Optional<Customer> checkCustomer = customerRepository.findById(id);
        if (!checkCustomer.isPresent()){
            return new PostCustomerResponse(HttpStatus.BAD_REQUEST.value(), "Không tồn tại khách hàng");
        }
        Customer customer = checkCustomer.get();
        customer.setIsDeleted(true);
        customer.setDeletedAt(DateTimeUtils.getCurrentDate());
        customerRepository.save(customer);
        return new PostCustomerResponse(HttpStatus.OK.value(), "Xóa khách hàng thành công");
    }

    @Override
    public Page<Customer> getCustomers(GetCustomersRequest getCustomersRequest) {
        Sort sort = Sort.unsorted();
        if (getCustomersRequest.getFilter() != null) {
            sort = Sort.by(getCustomersRequest.getFilter()).descending();
        }

        PageRequest pageRequest = PageRequest.of(getCustomersRequest.getPageNumber(),
                getCustomersRequest.getPageSize(),
                sort);

        Page<Customer> customers = customerRepository.findAllByIsDeletedFalse(pageRequest);

        String searchQuery = getCustomersRequest.getSearch();
        if (!StringUtils.isEmpty(searchQuery)) {
            searchQuery = "%" + searchQuery.toLowerCase() + "%";
            customers = customerRepository.findAllByCompanyNameLikeIgnoreCaseAndIsDeletedFalseOrTaxCodeLikeIgnoreCaseAndIsDeletedFalse(
                    searchQuery, searchQuery, pageRequest
            );
        }

        return customers;
    }
    @Override
    public QLCNDtoResponse getCustomersByCountOrder(GetCustomersRequest getCustomersRequest) {
        Sort sort = Sort.unsorted();
        if (getCustomersRequest.getFilter() != null) {
            sort = Sort.by(getCustomersRequest.getFilter()).descending();
        }

        PageRequest pageRequest = PageRequest.of(getCustomersRequest.getPageNumber(),
                getCustomersRequest.getPageSize(),
                sort);
        Page<Object[]> results = orderRepository.findCusWithOrder(pageRequest);
        QLCNDtoResponse qlcnDtoResponse = new QLCNDtoResponse();
        qlcnDtoResponse.setTotalPages(results.getTotalPages());
        List<QLCNDto> qlcnDtoList = new ArrayList<>();
        for (Object[] rs : results){
            Customer cus = (Customer) rs[0];
            Long count = (long) rs[1];
            double leftAmount = (double) rs[2];
            QLCNDto qlcnDto = new QLCNDto();
            qlcnDto.setCustomer(cus);
            qlcnDto.setOrderCount(count);
            qlcnDto.setTotalLeftAmount(leftAmount);
            qlcnDtoList.add(qlcnDto);
        }
        qlcnDtoResponse.setContent(qlcnDtoList);
        return qlcnDtoResponse;
    }
    @Override
    public int countCustomer(){
        return customerRepository.countByIsDeletedFalse();
    }
}
