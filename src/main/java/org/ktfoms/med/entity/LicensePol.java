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
import org.ktfoms.med.dto.LicensePolDto;
import org.ktfoms.med.form.LicensePolForm;
import org.ktfoms.med.form.LicenseStacForm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "license_pol")
public class LicensePol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "license_pol_id")
    private Integer id;

    @Column(name = "mcod")
    private Integer mcod;

    @Column(name = "spez_code")
    private String spez;

    @Column(name = "age_code")
    private String age;

    @Column(name = "category_code")
    private String category;

    @Column(name = "date_beg")
    private LocalDate dateBeg;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Column(name = "med_spec_v021")
    private Integer medSpecV021;


    public LicensePol (LicensePolDto dto){
        this.mcod = dto.getMcod();
        this.spez = dto.getSpez();
        this.age = dto.getAge();
        this.category = dto.getCategory();
        this.dateBeg = LocalDate.parse(dto.getDateBeg(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        this.dateEnd = LocalDate.parse(dto.getDateEnd(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
    }

    public LicensePol(LicensePolForm form) {
        this.mcod = form.getMcod();
        if(!form.getSpez().isBlank()) {this.spez = form.getSpez();}
        if(form.getMedSpecV021() != 0){this.medSpecV021 = form.getMedSpecV021();}
        this.age = form.getAge();
        this.category = form.getCategory();
        this.dateBeg = LocalDate.parse(form.getDateBeg());
        this.dateEnd = LocalDate.of(9999, 12, 31);
    }
}
