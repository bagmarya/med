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
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "zap")
public class MedSpecV021Dto {
    @JacksonXmlProperty(localName = "IDSPEC")
    private Integer idSpec;
    @JacksonXmlProperty(localName = "SPECNAME")
    private String specName;
    @JacksonXmlProperty(localName = "POSTNAME")
    private String postName;
    @JacksonXmlProperty(localName = "IDPOST_MZ")
    private Integer idPostMz;
    @JacksonXmlProperty(localName = "DATEBEG")
    private String dateBeg;
    @JacksonXmlProperty(localName = "DATEEND")
    private String dateEnd;
}
