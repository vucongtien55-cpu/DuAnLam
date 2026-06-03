package com.yourcompany.website.service;

import com.yourcompany.website.dto.request.ProductRequest;
import com.yourcompany.website.dto.response.PageResponse;
import com.yourcompany.website.dto.response.ProductCategoryResponse;
import com.yourcompany.website.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    // Category
    List<ProductCategoryResponse> getAllCategories();
    ProductCategoryResponse getCategoryBySlug(String slug);

    // Product
    PageResponse<ProductResponse> getProducts(int page, int size);
    PageResponse<ProductResponse> getProductsByCategory(Long categoryId, int page, int size);
    PageResponse<ProductResponse> searchProducts(String keyword, int page, int size);
    List<ProductResponse> getFeaturedProducts();
    ProductResponse getProductBySlug(String slug);

    // Admin
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
    void toggleActive(Long id);
}