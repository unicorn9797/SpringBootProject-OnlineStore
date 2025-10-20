package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "porder")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Porder {

    @Id
    @Column(name = "porder_id", length = 45, nullable = false, updatable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference(value = "member-porders") // 👈 命名成對
    private Member member;

    @Column(name = "totalPrice")
    private Double totalPrice;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @Column(name = "remark", length = 100)
    private String remark;

    @OneToMany(mappedBy = "porder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "porder-details") // 👈 命名成對
    private List<PorderDetail> details;
}
