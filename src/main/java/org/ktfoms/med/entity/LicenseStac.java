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
import org.ktfoms.med.dto.LicenseStacDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "license_stac")
public class LicenseStac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "license_stac_id")
    private Integer id;

    @Column(name = "mcod")
    private Integer mcod;

    @Column(name = "profil_v002")
    private Integer profil;

    @Column(name = "age_code")
    private String age;

    @Column(name = "stac_type_code")
    private Integer stacType;

    @Column(name = "pay_type_code")
    private Integer payType;

    @Column(name = "date_beg")
    private LocalDate dateBeg;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    public LicenseStac (LicenseStacDto dto){
        this.mcod = dto.getMcod();
        this.profil = dto.getProfil();
        this.age = dto.getAge();
        this.stacType = dto.getStacType();
        this.payType = dto.getPayType();
        this.dateBeg = LocalDate.parse(dto.getDateBeg(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        this.dateEnd = LocalDate.parse(dto.getDateEnd(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
    }

}
