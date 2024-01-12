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
@JacksonXmlRootElement(localName = "entry")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentDto {
    @JacksonXmlProperty(localName = "mo_oid")
    private String moOid;
    @JacksonXmlProperty(localName = "depart_oid")
    private String departOid;
    @JacksonXmlProperty(localName = "depart_create_date")
    private String dateBeg;
    @JacksonXmlProperty(localName = "depart_modify_date")
    private String dateMod;
    @JacksonXmlProperty(localName = "depart_liquidation_date")
    private String dateLiq;
    @JacksonXmlProperty(localName = "depart_name")
    private String departName;
    @JacksonXmlProperty(localName = "depart_type_id")
    private Integer departTypeCode;
    @JacksonXmlProperty(localName = "depart_type_name")
    private String departTypeName;
    @JacksonXmlProperty(localName = "depart_kind_id")
    private Integer departKindCode;
    @JacksonXmlProperty(localName = "depart_kind_name")
    private String departKindName;
    @JacksonXmlProperty(localName = "building_address_region_id")
    private Integer region;

    @JacksonXmlProperty(localName = "building_address_prefix_area")
    private String prefixArea;
    @JacksonXmlProperty(localName = "building_address_area_name")
    private String areaName;
    @JacksonXmlProperty(localName = "building_address_prefix_street")
    private String prefixStreet;
    @JacksonXmlProperty(localName = "building_address_street_name")
    private String streetName;
    @JacksonXmlProperty(localName = "building_address_house")
    private String addressHouse;
    @JacksonXmlProperty(localName = "building_address_building")
    private String addressBuilding;
    @JacksonXmlProperty(localName = "building_address_struct")
    private String addressStruct;
}
