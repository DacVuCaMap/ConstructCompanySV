package com.app.ConStructCompany.Repository;

import com.app.ConStructCompany.Entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByTaxCode(String taxCode);
    Page<Customer> findAllByCompanyNameLikeIgnoreCaseAndIsDeletedFalseOrTaxCodeLikeIgnoreCaseAndIsDeletedFalse(String search, String d, PageRequest pageRequest);
    Page<Customer> findAllByIsDeletedFalse(Pageable pageable);

    Customer findByPhoneNumber(String phoneNumber);
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.isDeleted=false")
    int countByIsDeletedFalse();
}
