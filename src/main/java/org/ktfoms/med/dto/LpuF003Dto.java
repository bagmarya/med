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
@JacksonXmlRootElement(localName = "medCompany")
public class LpuF003Dto {

    @JacksonXmlProperty(localName = "mcod")
    private Integer mcod;

    @JacksonXmlProperty(localName = "nam_mop")
    private  String mNameF;

    @JacksonXmlProperty(localName = "nam_mok")
    private  String mNameS;

    @JacksonXmlProperty(localName = "inn")
    private Long lpuinn;

    @JacksonXmlProperty(localName = "Ogrn")
    private Long ogrn;

    @JacksonXmlProperty(localName = "KPP")
    private  Integer kpp;

    @JacksonXmlProperty(localName = "index_j")
    private  Integer postId;

    @JacksonXmlProperty(localName = "fam_ruk")
    private String famRuk;
    @JacksonXmlProperty(localName = "im_ruk")
    private String imRuk;
    @JacksonXmlProperty(localName = "ot_ruk")
    private String otRuk;

    @JacksonXmlProperty(localName = "phone1")
    private  String tel;
    @JacksonXmlProperty(localName = "fax1")
    private  String fax;
    @JacksonXmlProperty(localName = "e_mail1")
    private  String eMail;

    @JacksonXmlProperty(localName = "d_begin")
    private String dateBeg;
    @JacksonXmlProperty(localName = "d_end")
    private String dateEnd;

}
