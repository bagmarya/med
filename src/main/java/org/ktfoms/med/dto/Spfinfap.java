package org.ktfoms.med.dto;


//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


//import jakarta.xml.bind.annotation.XmlElement;
//import jakarta.xml.bind.annotation.XmlElementWrapper;
//import jakarta.xml.bind.annotation.XmlRootElement;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString


@JacksonXmlRootElement(localName = "spfinfap")
public class Spfinfap {

    @JacksonXmlProperty
    private String version;

    @JacksonXmlProperty
    private String date;
    @JacksonXmlProperty(localName = "FAP")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<FapFinDto> FAP;


    public Spfinfap(String version, String date, List<FapFinDto> FAP) {
        this.version = version;
        this.date = date;
        this.FAP = FAP;
    }


}

