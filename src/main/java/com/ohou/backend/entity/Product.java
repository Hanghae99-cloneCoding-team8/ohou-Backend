package com.ohou.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private CategoryEnum category;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String brand;

    @Column
    private int discountRate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductImages> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductDetailImages> productDetailImages = new ArrayList<>();
}