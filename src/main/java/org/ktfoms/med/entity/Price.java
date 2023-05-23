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
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Integer id;

    @Column(name = "kod")
    private String kod;

    @Column(name = "spez")
    private String spez;

    @Column(name = "mkr")
    private String mkr;

    @Column(name = "gd")
    private Double gd;

    @Column(name = "gv")
    private Double gv;

    @Column(name = "rd")
    private Double rd;

    @Column(name = "rv")
    private Double rv;

}
