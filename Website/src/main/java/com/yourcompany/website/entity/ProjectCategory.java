package com.yourcompany.website.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(unique = true, nullable = false, length = 200)
    private String slug;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "is_active")
    private Boolean isActive = true;
}