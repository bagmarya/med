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
public class DirectorF036Dto {
    @JacksonXmlProperty(localName = "OID_MO")
    private String oid;

    @JacksonXmlProperty(localName = "FAM_RUK")
    private  String famRuk;
    @JacksonXmlProperty(localName = "IM_RUK")
    private  String imRuk;
    @JacksonXmlProperty(localName = "OT_RUK")
    private  String otRuk;

    @JacksonXmlProperty(localName = "DATEBEG")
    private String datebeg;
    @JacksonXmlProperty(localName = "DATEEND")
    private String dateend;
}
