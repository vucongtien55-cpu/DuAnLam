package com.yourcompany.website.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductRequest {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    private Long categoryId;
    private String description;
    private String content;
    private BigDecimal price;
    private BigDecimal salePrice;
    private String sku;
    private String thumbnail;
    private Boolean isFeatured = false;
    private Boolean isActive = true;
    private Integer sortOrder = 0;
    private String metaTitle;
    private String metaDesc;
}