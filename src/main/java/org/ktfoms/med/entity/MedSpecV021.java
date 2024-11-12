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
import org.ktfoms.med.dto.MedSpecV021Dto;
import org.ktfoms.med.dto.ProfilDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "sp_v021_med_spec")
public class MedSpecV021 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sp_v021_med_spec_id")
    private Integer id;

    @Column(name = "idspec")
    private Integer idSpec;

    @Column(name = "specname")
    private String specName;

    @Column(name = "postname")
    private String postName;

    @Column(name = "idpost_mz")
    private Integer idPostMz;

    @Column (name = "datebeg")
    private LocalDate dateBeg;
    @Column (name = "dateend")
    private LocalDate dateEnd;


    public MedSpecV021(MedSpecV021Dto dto) {
        this.idSpec = dto.getIdSpec();
        this.specName = dto.getSpecName();
        if (dto.getPostName() != null && !dto.getPostName().isBlank()) {this.postName = dto.getPostName();}
        this.idPostMz = dto.getIdPostMz();
        if (dto.getDateBeg() != null && !dto.getDateBeg().isBlank()) {this.dateBeg = LocalDate.parse(dto.getDateBeg(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));}
        if (dto.getDateEnd() != null && !dto.getDateEnd().isBlank()) {this.dateEnd = LocalDate.parse(dto.getDateEnd(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));}
    }
}
