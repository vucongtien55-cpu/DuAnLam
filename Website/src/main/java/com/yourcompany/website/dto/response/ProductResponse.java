package com.yourcompany.website.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String slug;
    private String categoryName;
    private Long categoryId;
    private String description;
    private String content;
    private BigDecimal price;
    private BigDecimal salePrice;
    private String sku;
    private String thumbnail;
    private List<String> images;
    private Boolean isFeatured;
    private Integer viewCount;
    private String metaTitle;
    private String metaDesc;
    private LocalDateTime createdAt;
}