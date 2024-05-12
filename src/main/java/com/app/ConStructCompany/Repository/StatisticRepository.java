package com.app.ConStructCompany.Repository;

import com.app.ConStructCompany.Entity.Statistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    Page<Statistic> findAllByIsDeletedFalse(Pageable pageable);
    List<Statistic> findAllByOrderIdAndIsDeletedFalseOrderByCreateAtAsc(Long id);

    Optional<Statistic> findFirstByOrderIdAndIsDeletedFalseOrderByCreateAtDesc(Long id);
    @Query("SELECT COUNT(s) FROM Statistic s WHERE s.isDeleted=false")
    int countByIsDeletedFalse();

    Page<Statistic> findAllByCustomerCompanyNameContainingIgnoreCaseAndIsDeletedFalse(String customerName,Pageable pageable);
}
