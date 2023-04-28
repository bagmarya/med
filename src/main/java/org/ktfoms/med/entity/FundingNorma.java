package org.ktfoms.med.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "funding_norma")
public class FundingNorma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_norma_id")
    private Integer id;


    @Column(name = "lpu_id")
    private Integer lpuId;

    @Column(name = "mo_lpu")
    private String moLpu;

    @Column(name = "m_namef")
    private String mNameF;

    @Column(name = "funding_date")
    private Date fundingDate;

    @Column(name = "quantity_in_Astr")
    private Integer quantityInAstr;

    @Column(name = "quantity_in_Kap")
    private Integer quantityInKap;

    @Column(name = "norma")
    private double norma;

}
