package org.ktfoms.med.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@JacksonXmlRootElement(localName = "License")
public class LicenseStacDto {
    @JacksonXmlProperty(localName = "MCOD")
    private Integer mcod;
    @JacksonXmlProperty(localName = "PROF_CODE")
    private Integer profil;
    @JacksonXmlProperty(localName = "VOZRAST")
    private String age;
    @JacksonXmlProperty(localName = "TIP_STAC")
    private Integer stacType;
    @JacksonXmlProperty(localName = "OPLAT")
    private Integer payType;
    @JacksonXmlProperty(localName = "DATE_BEG")
    private String dateBeg;
    @JacksonXmlProperty(localName = "DATE_END")
    private String dateEnd;
}