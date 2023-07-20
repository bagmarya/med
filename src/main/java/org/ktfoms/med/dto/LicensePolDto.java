package org.ktfoms.med.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class LicensePolDto {
    @JacksonXmlProperty(localName = "MCOD")
    private Integer mcod;
    @JacksonXmlProperty(localName = "SPEZ_CODE")
    private String spez;
    @JacksonXmlProperty(localName = "KATEGOR")
    private String category;
    @JacksonXmlProperty(localName = "VOZRAST")
    private String age;
    @JacksonXmlProperty(localName = "DATE_BEG")
    private String dateBeg;
    @JacksonXmlProperty(localName = "DATE_END")
    private String dateEnd;
}
