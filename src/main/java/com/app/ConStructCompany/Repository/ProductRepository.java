package com.app.ConStructCompany.Repository;

import com.app.ConStructCompany.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByDeletedFalse(Pageable pageable);

    Page<Product> findByProNameContainingIgnoreCaseAndDeletedFalse(String proName, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.deleted=false")
    int countByIsDeletedFalse();

    Optional<Product> findByProNameIgnoreCaseAndDeletedFalse(String proName);
}
