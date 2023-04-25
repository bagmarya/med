package org.ktfoms.med.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JacksonXmlRootElement(localName = "FAP")
public class FapFinDto {


    @JacksonXmlProperty(localName = "MKOD")
    private Integer mkod;
    @JacksonXmlProperty(localName = "NAME_PODR")
    private String namePodr;
    @JacksonXmlProperty(localName = "LPU")
    private String moLpu;
    @JacksonXmlProperty(localName = "PODR")
    private String podr;

    @JacksonXmlProperty(localName = "G_FIN1")
    private double gFin1;
    @JacksonXmlProperty(localName = "K_YKOMP1")
    private double kYkomp1;
    @JacksonXmlProperty(localName = "SUMM_ASTRA1")
    private double summAstra1;
    @JacksonXmlProperty(localName = "SUMM_KAPIT1")
    private double summKapit1;

    @JacksonXmlProperty(localName = "G_FIN2")
    private double gFin2;
    @JacksonXmlProperty(localName = "K_YKOMP2")
    private double kYkomp2;
    @JacksonXmlProperty(localName = "SUMM_ASTRA2")
    private double summAstra2;
    @JacksonXmlProperty(localName = "SUMM_KAPIT2")
    private double summKapit2;

    @JacksonXmlProperty(localName = "G_FIN3")
    private double gFin3;
    @JacksonXmlProperty(localName = "K_YKOMP3")
    private double kYkomp3;
    @JacksonXmlProperty(localName = "SUMM_ASTRA3")
    private double summAstra3;
    @JacksonXmlProperty(localName = "SUMM_KAPIT3")
    private double summKapit3;

    @JacksonXmlProperty(localName = "G_FIN4")
    private double gFin4;
    @JacksonXmlProperty(localName = "K_YKOMP4")
    private double kYkomp4;
    @JacksonXmlProperty(localName = "SUMM_ASTRA4")
    private double summAstra4;
    @JacksonXmlProperty(localName = "SUMM_KAPIT4")
    private double summKapit4;

    @JacksonXmlProperty(localName = "G_FIN5")
    private double gFin5;
    @JacksonXmlProperty(localName = "K_YKOMP5")
    private double kYkomp5;
    @JacksonXmlProperty(localName = "SUMM_ASTRA5")
    private double summAstra5;
    @JacksonXmlProperty(localName = "SUMM_KAPIT5")
    private double summKapit5;

    @JacksonXmlProperty(localName = "G_FIN6")
    private double gFin6;
    @JacksonXmlProperty(localName = "K_YKOMP6")
    private double kYkomp6;
    @JacksonXmlProperty(localName = "SUMM_ASTRA6")
    private double summAstra6;
    @JacksonXmlProperty(localName = "SUMM_KAPIT6")
    private double summKapit6;

    @JacksonXmlProperty(localName = "G_FIN7")
    private double gFin7;
    @JacksonXmlProperty(localName = "K_YKOMP7")
    private double kYkomp7;
    @JacksonXmlProperty(localName = "SUMM_ASTRA7")
    private double summAstra7;
    @JacksonXmlProperty(localName = "SUMM_KAPIT7")
    private double summKapit7;

    @JacksonXmlProperty(localName = "G_FIN8")
    private double gFin8;
    @JacksonXmlProperty(localName = "K_YKOMP8")
    private double kYkomp8;
    @JacksonXmlProperty(localName = "SUMM_ASTRA8")
    private double summAstra8;
    @JacksonXmlProperty(localName = "SUMM_KAPIT8")
    private double summKapit8;

    @JacksonXmlProperty(localName = "G_FIN9")
    private double gFin9;
    @JacksonXmlProperty(localName = "K_YKOMP9")
    private double kYkomp9;
    @JacksonXmlProperty(localName = "SUMM_ASTRA9")
    private double summAstra9;
    @JacksonXmlProperty(localName = "SUMM_KAPIT9")
    private double summKapit9;

    @JacksonXmlProperty(localName = "G_FIN10")
    private double gFin10;
    @JacksonXmlProperty(localName = "K_YKOMP10")
    private double kYkomp10;
    @JacksonXmlProperty(localName = "SUMM_ASTRA10")
    private double summAstra10;
    @JacksonXmlProperty(localName = "SUMM_KAPIT10")
    private double summKapit10;

    @JacksonXmlProperty(localName = "G_FIN11")
    private double gFin11;
    @JacksonXmlProperty(localName = "K_YKOMP11")
    private double kYkomp11;
    @JacksonXmlProperty(localName = "SUMM_ASTRA11")
    private double summAstra11;
    @JacksonXmlProperty(localName = "SUMM_KAPIT11")
    private double summKapit11;

    @JacksonXmlProperty(localName = "G_FIN12")
    private double gFin12;
    @JacksonXmlProperty(localName = "K_YKOMP12")
    private double kYkomp12;
    @JacksonXmlProperty(localName = "SUMM_ASTRA12")
    private double summAstra12;
    @JacksonXmlProperty(localName = "SUMM_KAPIT12")
    private double summKapit12;

    public FapFinDto(Integer mkod, String namePodr, String moLpu, String podr,
                     double gFin1, double kYkomp1, double summAstra1, double summKapit1,
                     double gFin2, double kYkomp2, double summAstra2, double summKapit2,
                     double gFin3, double kYkomp3, double summAstra3, double summKapit3,
                     double gFin4, double kYkomp4, double summAstra4, double summKapit4,
                     double gFin5, double kYkomp5, double summAstra5, double summKapit5,
                     double gFin6, double kYkomp6, double summAstra6, double summKapit6,
                     double gFin7, double kYkomp7, double summAstra7, double summKapit7,
                     double gFin8, double kYkomp8, double summAstra8, double summKapit8,
                     double gFin9, double kYkomp9, double summAstra9, double summKapit9,
                     double gFin10, double kYkomp10, double summAstra10, double summKapit10,
                     double gFin11, double kYkomp11, double summAstra11, double summKapit11,
                     double gFin12, double kYkomp12, double summAstra12, double summKapit12)
    {
        this.mkod = mkod;
        this.namePodr = namePodr;
        this.moLpu = moLpu;
        this.podr = podr;
        this.gFin1 = gFin1;
        this.kYkomp1 = kYkomp1;
        this.summAstra1 = summAstra1;
        this.summKapit1 = summKapit1;
        this.gFin2 = gFin2;
        this.kYkomp2 = kYkomp2;
        this.summAstra2 = summAstra2;
        this.summKapit2 = summKapit2;
        this.gFin3 = gFin3;
        this.kYkomp3 = kYkomp3;
        this.summAstra3 = summAstra3;
        this.summKapit3 = summKapit3;
        this.gFin4 = gFin4;
        this.kYkomp4 = kYkomp4;
        this.summAstra4 = summAstra4;
        this.summKapit4 = summKapit4;
        this.gFin5 = gFin5;
        this.kYkomp5 = kYkomp5;
        this.summAstra5 = summAstra5;
        this.summKapit5 = summKapit5;
        this.gFin6 = gFin6;
        this.kYkomp6 = kYkomp6;
        this.summAstra6 = summAstra6;
        this.summKapit6 = summKapit6;
        this.gFin7 = gFin7;
        this.kYkomp7 = kYkomp7;
        this.summAstra7 = summAstra7;
        this.summKapit7 = summKapit7;
        this.gFin8 = gFin8;
        this.kYkomp8 = kYkomp8;
        this.summAstra8 = summAstra8;
        this.summKapit8 = summKapit8;
        this.gFin9 = gFin9;
        this.kYkomp9 = kYkomp9;
        this.summAstra9 = summAstra9;
        this.summKapit9 = summKapit9;
        this.gFin10 = gFin10;
        this.kYkomp10 = kYkomp10;
        this.summAstra10 = summAstra10;
        this.summKapit10 = summKapit10;
        this.gFin11 = gFin11;
        this.kYkomp11 = kYkomp11;
        this.summAstra11 = summAstra11;
        this.summKapit11 = summKapit11;
        this.gFin12 = gFin12;
        this.kYkomp12 = kYkomp12;
        this.summAstra12 = summAstra12;
        this.summKapit12 = summKapit12;
    }
}
