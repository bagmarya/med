package org.ktfoms.med.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LicensePolForm {
    private Map<String, String> categoryNames;
    private Map<String, String> spezNames;
    private Map<String, String> ageNames;
    private Map<Integer, String> medSpecV021Names;

    private Integer mcod;
    private String spez;
    private String category;
    private String age;
    private String dateBeg;
    private String dateEnd;
    private Integer medSpecV021;
}
