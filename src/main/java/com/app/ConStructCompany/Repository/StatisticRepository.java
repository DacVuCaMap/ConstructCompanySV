package com.app.ConStructCompany.Repository;

import com.app.ConStructCompany.Entity.Statistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    Page<Statistic> findAllByIsDeletedFalse(Pageable pageable);

    @Query("SELECT COUNT(s) FROM Statistic s WHERE s.isDeleted=false")
    int countByIsDeletedFalse();
}
