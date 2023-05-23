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
@Table(name = "fys")
public class Fys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fys_id")
    private Integer id;

    @Column(name = "kod_sp")
    private String kodSp;

    @Column(name = "name_ysl")
    private String nameYsl;

    @Column(name = "kod_usl_mz")
    private String kodUslMz;

    @Column(name = "rz")
    private String rz;

    @Column(name = "typ")
    private String typ;

    @Column(name = "klas")
    private String klas;

    @Column(name = "vid")
    private String vid;

    @Column(name = "pvid")
    private String pvid;

    @Column(name = "oms")
    private boolean oms;

    @Column(name = "pos")
    private boolean pos;

    @Column(name = "mkr")
    private String mkr;

    @Column(name = "vrvr")
    private Double vrvr;

    @Column(name = "vrss")
    private Double vrss;

    @Column(name = "gd")
    private Double gd;

    @Column(name = "gv")
    private Double gv;

    @Column(name = "gdi")
    private Double gdi;

    @Column(name = "gvi")
    private Double gvi;

    @Column(name = "rd")
    private Double rd;

    @Column(name = "rv")
    private Double rv;

    @Column(name = "rdi")
    private Double rdi;

    @Column(name = "rvi")
    private Double rvi;


    @Column(name = "v021_d")
    private String v021D;

    @Column(name = "v021_v")
    private String v021V;

    @Column(name = "diag_n")
    private String diagN;

    @Column(name = "diag_k")
    private String diagK;

}



