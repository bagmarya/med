package org.ktfoms.med.dto;


//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


//import jakarta.xml.bind.annotation.XmlElement;
//import jakarta.xml.bind.annotation.XmlElementWrapper;
//import jakarta.xml.bind.annotation.XmlRootElement;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.Binder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString

//@XmlAccessorType(XmlAccessType.NONE)
//@XmlType(propOrder={"version", "date", "FAP"})
@XmlRootElement
public class Spfinfap implements Serializable {

    @XmlElement
    private String version;
    @XmlElement
    private String date;

    @XmlElementWrapper()
    private List<FapFinDto> FAP;


    public Spfinfap(String version, String date, List<FapFinDto> FAP) {
        this.version = version;
        this.date = date;
        this.FAP = FAP;
    }


}

