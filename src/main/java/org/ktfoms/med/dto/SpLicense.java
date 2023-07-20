package org.ktfoms.med.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JacksonXmlRootElement(localName = "Root")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpLicense {

    @JacksonXmlProperty(localName = "Stacionar")
    private List<LicenseStacDto> stacionar;

    @JacksonXmlProperty(localName = "Policlinic")
    private List<LicensePolDto> policlinic;
}
