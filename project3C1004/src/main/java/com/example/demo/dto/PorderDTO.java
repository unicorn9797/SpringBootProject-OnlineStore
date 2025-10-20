package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PorderDTO {
	private String id;
	private String memberId;
	private String memberName;	
	private Double totalPrice;
	private LocalDateTime dateTime;
	private String remark;
}
