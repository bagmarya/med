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
@JacksonXmlRootElement(localName = "book")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpDepartment {
    @JacksonXmlProperty(localName = "entries")
    private List<DepartmentDto> departments;
}
