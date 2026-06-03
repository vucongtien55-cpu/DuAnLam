package com.yourcompany.website.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "settings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 200)
    private String key;

    @Column(columnDefinition = "TEXT")
    private String value;

    @Column(length = 200)
    private String label;

    @Column(length = 100)
    private String grp;
}