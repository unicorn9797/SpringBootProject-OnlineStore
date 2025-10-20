package com.example.demo.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProductDTO {
	private String id;
    private String name;
    private BigDecimal price;
    private String categoryName;
    private String imageUrl;
}
