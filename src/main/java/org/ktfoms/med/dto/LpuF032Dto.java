package org.ktfoms.med.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
// F032 - Реестр медицинских организаций, осуществляющих деятельность в сфере обязательного медицинского страхования (версии 3.9)
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "zap")
public class LpuF032Dto {

    @JacksonXmlProperty(localName = "MCOD")
    private Integer mcod;

    @JacksonXmlProperty(localName = "OID_MO")
    private String oid;

    @JacksonXmlProperty(localName = "NAM_MOP")
    private  String mNameF;

    @JacksonXmlProperty(localName = "NAM_MOK")
    private  String mNameS;

    @JacksonXmlProperty(localName = "INN")
    private Long lpuinn;

    @JacksonXmlProperty(localName = "OGRN")
    private Long ogrn;

    @JacksonXmlProperty(localName = "KPP")
    private  Integer kpp;

    @JacksonXmlProperty(localName = "JURADDRESS_INDEX")
    private  Integer postId;

    @JacksonXmlProperty(localName = "JURADDRESS_ADDRESS")
    private  String address;

    @JacksonXmlProperty(localName = "PHONE")
    private  String tel;
    @JacksonXmlProperty(localName = "FAX")
    private  String fax;
    @JacksonXmlProperty(localName = "EMAIL")
    private  String eMail;

    @JacksonXmlProperty(localName = "D_BEGIN_OMS")
    private String dateBeg;
    @JacksonXmlProperty(localName = "D_END")
    private String dateEnd;

    @JacksonXmlProperty(localName = "DATEBEG")
    private String dateBegZap;
    @JacksonXmlProperty(localName = "DATEEND")
    private String dateEndZap;

}
