package com.yourcompany.website.service.impl;

import com.yourcompany.website.dto.request.ProductRequest;
import com.yourcompany.website.dto.response.PageResponse;
import com.yourcompany.website.dto.response.ProductCategoryResponse;
import com.yourcompany.website.dto.response.ProductResponse;
import com.yourcompany.website.entity.Product;
import com.yourcompany.website.entity.ProductCategory;
import com.yourcompany.website.entity.ProductImage;
import com.yourcompany.website.exception.ResourceNotFoundException;
import com.yourcompany.website.repository.ProductCategoryRepository;
import com.yourcompany.website.repository.ProductRepository;
import com.yourcompany.website.service.ProductService;
import com.yourcompany.website.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;

    // ==================== CATEGORY ====================

    @Override
    @Transactional(readOnly = true)
    public List<ProductCategoryResponse> getAllCategories() {
        return categoryRepository
                .findByParentIsNullAndIsActiveTrueOrderBySortOrderAsc()
                .stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductCategoryResponse getCategoryBySlug(String slug) {
        ProductCategory category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục: " + slug));
        return mapToCategoryResponse(category);
    }

    // ==================== PRODUCT ====================

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("sortOrder").ascending());
        Page<Product> productPage = productRepository.findByIsActiveTrueOrderBySortOrderAscCreatedAtDesc(pageable);
        return mapToPageResponse(productPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> getProductsByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findByCategoryIdAndIsActiveTrue(categoryId, pageable);
        return mapToPageResponse(productPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.searchByKeyword(keyword, pageable);
        return mapToPageResponse(productPage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrueAndIsActiveTrueOrderBySortOrderAsc()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm: " + slug));
        productRepository.incrementViewCount(product.getId());
        return mapToProductResponse(product);
    }

    // ==================== ADMIN ====================

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        String slug = SlugUtil.generateUniqueSlug(
                request.getName(),
                productRepository.existsBySlug(SlugUtil.toSlug(request.getName()))
        );

        Product product = Product.builder()
                .name(request.getName())
                .slug(slug)
                .description(request.getDescription())
                .content(request.getContent())
                .price(request.getPrice())
                .salePrice(request.getSalePrice())
                .sku(request.getSku())
                .thumbnail(request.getThumbnail())
                .isFeatured(request.getIsFeatured())
                .isActive(request.getIsActive())
                .sortOrder(request.getSortOrder())
                .metaTitle(request.getMetaTitle())
                .metaDesc(request.getMetaDesc())
                .build();

        if (request.getCategoryId() != null) {
            categoryRepository.findById(request.getCategoryId())
                    .ifPresent(product::setCategory);
        }

        return mapToProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm id: " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setContent(request.getContent());
        product.setPrice(request.getPrice());
        product.setSalePrice(request.getSalePrice());
        product.setSku(request.getSku());
        product.setIsFeatured(request.getIsFeatured());
        product.setIsActive(request.getIsActive());
        product.setSortOrder(request.getSortOrder());
        product.setMetaTitle(request.getMetaTitle());
        product.setMetaDesc(request.getMetaDesc());

        if (request.getThumbnail() != null) {
            product.setThumbnail(request.getThumbnail());
        }

        if (request.getCategoryId() != null) {
            categoryRepository.findById(request.getCategoryId())
                    .ifPresent(product::setCategory);
        }

        return mapToProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy sản phẩm id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public void toggleActive(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm id: " + id));
        product.setIsActive(!product.getIsActive());
        productRepository.save(product);
    }

    // ==================== MAPPER ====================

    private ProductCategoryResponse mapToCategoryResponse(ProductCategory cat) {
        List<ProductCategoryResponse> children = cat.getChildren() == null ? List.of() :
                cat.getChildren().stream()
                        .filter(c -> Boolean.TRUE.equals(c.getIsActive()))
                        .map(this::mapToCategoryResponse)
                        .collect(Collectors.toList());

        return ProductCategoryResponse.builder()
                .id(cat.getId())
                .name(cat.getName())
                .slug(cat.getSlug())
                .description(cat.getDescription())
                .imageUrl(cat.getImageUrl())
                .sortOrder(cat.getSortOrder())
                .children(children)
                .build();
    }

    private ProductResponse mapToProductResponse(Product p) {
        List<String> images = p.getImages() == null ? List.of() :
                p.getImages().stream()
                        .map(ProductImage::getImageUrl)
                        .collect(Collectors.toList());

        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .slug(p.getSlug())
                .categoryId(p.getCategory() != null ? p.getCategory().getId() : null)
                .categoryName(p.getCategory() != null ? p.getCategory().getName() : null)
                .description(p.getDescription())
                .content(p.getContent())
                .price(p.getPrice())
                .salePrice(p.getSalePrice())
                .sku(p.getSku())
                .thumbnail(p.getThumbnail())
                .images(images)
                .isFeatured(p.getIsFeatured())
                .viewCount(p.getViewCount())
                .metaTitle(p.getMetaTitle())
                .metaDesc(p.getMetaDesc())
                .createdAt(p.getCreatedAt())
                .build();
    }

    private PageResponse<ProductResponse> mapToPageResponse(Page<Product> page) {
        return PageResponse.<ProductResponse>builder()
                .content(page.getContent().stream()
                        .map(this::mapToProductResponse)
                        .collect(Collectors.toList()))
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}