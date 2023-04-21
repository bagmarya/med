package org.ktfoms.med.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FapDto {

    private String moLpu;
    private String podr;
    private Date dateN;
    private Date dateLik;
    private String namePodr;
    private Date dnLicen;
    private Date dkLicen;
    private Integer kodTipePodr;
    private String nameTipePodr;
    private Integer kodVidPodr;
    private String nameVidPodr;
    private Integer mkod;

    public FapDto() {
    }


    public FapDto(String moLpu,
                  String podr,
                  Date dateN,
                  Date dateLik,
                  String namePodr,
                  Date dnLicen,
                  Date dkLicen,
                  Integer kodTipePodr,
                  String nameTipePodr,
                  Integer kodVidPodr,
                  String nameVidPodr,
                  Integer mkod) {
        this.moLpu = moLpu;
        this.podr = podr;
        this.dateN = dateN;
        this.dateLik = dateLik;
        this.namePodr = namePodr;
        this.dnLicen = dnLicen;
        this.dkLicen = dkLicen;
        this.kodTipePodr = kodTipePodr;
        this.nameTipePodr = nameTipePodr;
        this.kodVidPodr = kodVidPodr;
        this.nameVidPodr = nameVidPodr;
        this.mkod = mkod;
    }
}
