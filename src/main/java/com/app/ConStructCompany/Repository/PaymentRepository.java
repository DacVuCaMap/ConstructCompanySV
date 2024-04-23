package com.app.ConStructCompany.Repository;

import com.app.ConStructCompany.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findAllByOrderId(Long id);
}
