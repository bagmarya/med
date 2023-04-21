package org.ktfoms.med.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fap_fin")
public class FapFin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fap_fin_id")
    private Integer id;

    @Column(name = "podr")
    private String podr;
//    unique references fap (podr),

    @Column(name = "g_fin1")
    private double gFin1;
//    money not null default 0,

    @Column(name = "k_ykomp1")
    private double kYkomp1;
//    numeric(3,2) not null default 0,

    @Column(name = "summ_astra1")
    private double summAstra1;
//    money not null default 0,

    @Column(name = "summ_kapit1")
    private double summKapit1;
//    money not null default 0,

    //block 2
    @Column(name = "g_fin2")
    private double gFin2;
    @Column(name = "k_ykomp2")
    private double kYkomp2;
    @Column(name = "summ_astra2")
    private double summAstra2;
    @Column(name = "summ_kapit2")
    private double summKapit2;

    //block 3
    @Column(name = "g_fin3")
    private double gFin3;
    @Column(name = "k_ykomp3")
    private double kYkomp3;
    @Column(name = "summ_astra3")
    private double summAstra3;
    @Column(name = "summ_kapit3")
    private double summKapit3;

    //block 4
    @Column(name = "g_fin4")
    private double gFin4;
    @Column(name = "k_ykomp4")
    private double kYkomp4;
    @Column(name = "summ_astra4")
    private double summAstra4;
    @Column(name = "summ_kapit4")
    private double summKapit4;

    //block 5
    @Column(name = "g_fin5")
    private double gFin5;
    @Column(name = "k_ykomp5")
    private double kYkomp5;
    @Column(name = "summ_astra5")
    private double summAstra5;
    @Column(name = "summ_kapit5")
    private double summKapit5;

    //block 6
    @Column(name = "g_fin6")
    private double gFin6;
    @Column(name = "k_ykomp6")
    private double kYkomp6;
    @Column(name = "summ_astra6")
    private double summAstra6;
    @Column(name = "summ_kapit6")
    private double summKapit6;

    //block 7
    @Column(name = "g_fin7")
    private double gFin7;
    @Column(name = "k_ykomp7")
    private double kYkomp7;
    @Column(name = "summ_astra7")
    private double summAstra7;
    @Column(name = "summ_kapit7")
    private double summKapit7;

    //block 8
    @Column(name = "g_fin8")
    private double gFin8;
    @Column(name = "k_ykomp8")
    private double kYkomp8;
    @Column(name = "summ_astra8")
    private double summAstra8;
    @Column(name = "summ_kapit8")
    private double summKapit8;

    //block 9
    @Column(name = "g_fin9")
    private double gFin9;
    @Column(name = "k_ykomp9")
    private double kYkomp9;
    @Column(name = "summ_astra9")
    private double summAstra9;
    @Column(name = "summ_kapit9")
    private double summKapit9;

    //block 10
    @Column(name = "g_fin10")
    private double gFin10;
    @Column(name = "k_ykomp10")
    private double kYkomp10;
    @Column(name = "summ_astra10")
    private double summAstra10;
    @Column(name = "summ_kapit10")
    private double summKapit10;

    //block 11
    @Column(name = "g_fin11")
    private double gFin11;
    @Column(name = "k_ykomp11")
    private double kYkomp11;
    @Column(name = "summ_astra11")
    private double summAstra11;
    @Column(name = "summ_kapit11")
    private double summKapit11;

    //block 12
    @Column(name = "g_fin12")
    private double gFin12;
    @Column(name = "k_ykomp12")
    private double kYkomp12;
    @Column(name = "summ_astra12")
    private double summAstra12;
    @Column(name = "summ_kapit12")
    private double summKapit12;




}
