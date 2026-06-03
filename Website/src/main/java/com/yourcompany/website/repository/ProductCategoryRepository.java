package com.yourcompany.website.repository;

import com.yourcompany.website.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findByIsActiveTrueOrderBySortOrderAsc();

    List<ProductCategory> findByParentIsNullAndIsActiveTrueOrderBySortOrderAsc();

    Optional<ProductCategory> findBySlug(String slug);

    boolean existsBySlug(String slug);
}