package org.ktfoms.med.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "fap")
public class Fap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fap_id")
    private Integer id;

    @Column(name = "mo_lpu")
    private String moLpu;
//    references lpu (kod_sp),

    @Column(name = "podr")
    private String podr;
//    varchar(50) not null unique,

    @Column(name = "date_n")
    private Date dateN;

    @Column(name = "date_lik")
    private Date dateLik;

    @Column(name = "name_podr")
    private String namePodr;

    @Column(name = "dn_licen")
    private Date dnLicen;

    @Column(name = "dk_licen")
    private Date dkLicen;

    @Column(name = "kod_tipe_podr")
    private Integer kodTipePodr;

    @Column(name = "name_tipe_podr")
    private String nameTipePodr;

    @Column(name = "kod_vid_podr")
    private Integer kodVidPodr;

    @Column(name = "name_vid_podr")
    private String nameVidPodr;

    public Fap() {
    }
}
