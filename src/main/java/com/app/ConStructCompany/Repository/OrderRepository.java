package com.app.ConStructCompany.Repository;

import com.app.ConStructCompany.Entity.Customer;
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
    Page<Order> findAllListWithConditions1(String key, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.customer.id=:key " +
            "AND isDeleted=false")
    Page<Order> findAllListWithConditions2(String key, Pageable pageable);

    Page<Order> findAllByIsDeletedFalse(Pageable pageable);
    List<Order> findByIsDeletedFalseAndIsPaymentedFalse();

    @Query("SELECT c,COUNT(o.id),SUM(o.leftAmount) FROM Order o INNER JOIN Customer c on o.customer.id=c.id WHERE o.isDeleted=false AND c.isDeleted=false " +
            "GROUP BY c.id")
    Page<Object[]> findCusWithOrder(Pageable pageable);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.isDeleted=false")
    int countByIsDeletedFalse();

}
