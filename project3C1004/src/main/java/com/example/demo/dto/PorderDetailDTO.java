package com.example.demo.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PorderDetailDTO {
	private String id;
	private String porderId;
	private String productId;
	private String productName;
	private BigDecimal unitPrice;
	private Integer quantity;
}
