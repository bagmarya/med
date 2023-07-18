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
public class ProfilDto {

    @JacksonXmlProperty(localName = "IDPR")
    private Integer idpr;

    @JacksonXmlProperty(localName = "PRNAME")
    private String prName;

    @JacksonXmlProperty(localName = "DATEEND")
    private String dateEnd;
}
