package org.ktfoms.med.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JacksonXmlRootElement(localName = "ZAP")
public class FundingNormaSmpDto {

    @JacksonXmlProperty(localName = "USL_OK")
    private Integer uslOk;
    @JacksonXmlProperty(localName = "SMO")
    private Integer smo;
    @JacksonXmlProperty(localName = "MCOD")
    private Integer mcod;
    @JacksonXmlProperty(localName = "KOL_ZL")
    private Integer kolZl;
    @JacksonXmlProperty(localName = "TARIF")
    private Double tarif;
    @JacksonXmlProperty(localName = "DATEBEG")
    private String datebeg;
    @JacksonXmlProperty(localName = "DATEEND")
    private String dateend;
}
