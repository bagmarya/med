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
import org.ktfoms.med.dto.DepartmentDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer id;

    @Column(name = "mo_oid")
    private String moLpu;
//    references lpu (kod_sp),

    @Column(name = "depart_oid")
    private String podr;
//    varchar(50) not null unique,

    @Column(name = "depart_create_date")
    private LocalDate dateN;

    @Column(name = "depart_modify_date")
    private LocalDate dateModify;

    @Column(name = "depart_liquidation_date")
    private LocalDate dateLiq;

    @Column(name = "depart_name")
    private String namePodr;

    @Column(name = "depart_type_id")
    private Integer kodTipePodr;

    @Column(name = "depart_type_name")
    private String nameTipePodr;

    @Column(name = "depart_kind_id")
    private Integer kodVidPodr;

    @Column(name = "depart_kind_name")
    private String nameVidPodr;

    @Column(name = "address")
    private String address;


    public Department(DepartmentDto dto) {
        this.moLpu = dto.getMoOid();
        this.podr = dto.getDepartOid();
        this.dateN = LocalDate.parse(dto.getDateBeg().substring(0, 10));
        this.dateModify = LocalDate.parse(dto.getDateMod().substring(0, 10));
        if (dto.getDateLiq() != null) {this.dateLiq = LocalDate.parse(dto.getDateLiq(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));}
        this.namePodr = dto.getDepartName();
        this.kodTipePodr = dto.getDepartTypeCode();
        this.nameTipePodr = dto.getDepartTypeName();
        this.kodVidPodr = dto.getDepartKindCode();
        this.nameVidPodr = dto.getDepartKindName();
        this.address =
                ((dto.getPrefixArea() == null) ? "" : dto.getPrefixArea() + ". ") +
                ((dto.getAreaName() == null) ? "" : dto.getAreaName()+  ", ")  +
                ((dto.getPrefixStreet() == null) ? "" : dto.getPrefixStreet()+  ". ")  +
                        ((dto.getStreetName() == null) ? "" : dto.getStreetName()+  ", ")  +
                        ((dto.getAddressHouse() == null) ? "" : "д." + dto.getAddressHouse()+  " ")  +
                        ((dto.getAddressBuilding() == null) ? "" : "стр." + dto.getAddressBuilding()+  " ")  +
                        ((dto.getAddressStruct() == null) ? "" : "корп." + dto.getAddressStruct()+  " ");
    }
}
