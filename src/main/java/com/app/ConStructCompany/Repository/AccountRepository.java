package com.app.ConStructCompany.Repository;

import com.app.ConStructCompany.Entity.Account;
import com.app.ConStructCompany.Entity.Statistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account>findByEmail(String email);
    Page<Account> findAllByIsDeletedFalse(Pageable pageable);
}
