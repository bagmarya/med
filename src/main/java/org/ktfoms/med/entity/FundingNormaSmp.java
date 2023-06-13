package org.ktfoms.med.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ktfoms.med.dto.FundingNormaSmpDto;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "funding_norma_smp")
public class FundingNormaSmp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funding_norma_smp_id")
    private Integer id;

    @Column(name = "usl_ok")
    private Integer uslOk;

    @Column(name = "smo")
    private Integer smo;

    @Column(name = "mcod")
    private Integer mcod;

    @Column(name = "kol_zl")
    private Integer kolZl;

    @Column(name = "tarif")
    private Double tarif;

    @Column(name = "datebeg")
    private LocalDate datebeg;

    @Column(name = "dateend")
    private LocalDate dateend;

    public FundingNormaSmp(FundingNormaSmpDto dto){
        this.uslOk = dto.getUslOk();
        this.smo = dto.getSmo();
        this.mcod = dto.getMcod();
        this.kolZl = dto.getKolZl();
        this.tarif = dto.getTarif();
        this.datebeg = LocalDate.parse(dto.getDatebeg());
        this.dateend = LocalDate.parse(dto.getDateend());
    }
}
