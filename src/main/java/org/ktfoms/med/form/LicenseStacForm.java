package org.ktfoms.med.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LicenseStacForm {
    private Map<Integer, String> stacTypeNames;
    private Map<Integer, String> profilNames;
    private Map<String, String> ageNames;
    private Map<Integer, String> payTypeNames;

    private Integer mcod;
    private Integer stacType;
    private Integer profil;
    private String age;
    private Integer payType;
    private String dateBeg;

}
