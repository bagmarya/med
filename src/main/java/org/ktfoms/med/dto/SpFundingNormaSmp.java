package org.ktfoms.med.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JacksonXmlRootElement(localName = "PACKET")
public class SpFundingNormaSmp {

    @JacksonXmlProperty(localName = "ZGLV")
    private String spravName;

    @JacksonXmlProperty(localName = "VERSION")
    private String version;

    @JacksonXmlProperty(localName = "DATA")
    private String date;

    @JacksonXmlProperty(localName = "ZAP")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<FundingNormaSmpDto> zap;
}
