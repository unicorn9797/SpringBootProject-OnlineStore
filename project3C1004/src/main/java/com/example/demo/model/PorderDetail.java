package com.example.demo.model;


import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "porderdetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PorderDetail {

    @Id
    @Column(name = "porderDetail_id", nullable = false, length = 45, updatable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "porder_id", nullable = false)
    @JsonBackReference(value = "porder-details") // 👈 對應上面
    private Porder porder;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference(value = "category-products") // 👈 對應 Product → Category
    private Product product;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}

