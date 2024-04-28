package com.app.ConStructCompany.Repository;

import com.app.ConStructCompany.Entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findFirstByOrderByIdDesc();
    @Query("SELECT o FROM Order o WHERE (o.contractCode LIKE :key OR o.customer.companyName LIKE :key)" +
            "AND isDeleted=false")
    Page<Order> findAllListWithConditions(String key, Pageable pageable);
    Page<Order> findAllByIsDeletedFalse(Pageable pageable);
    List<Order> findByIsDeletedFalseAndIsPaymentedFalse();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.isDeleted=false")
    int countByIsDeletedFalse();

}
