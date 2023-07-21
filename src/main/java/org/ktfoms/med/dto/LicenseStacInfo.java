package org.ktfoms.med.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LicenseStacInfo {
    private Integer id;

    private Integer mcod;

    private String profil;

    private String age;

    private String stacType;

    private String payType;

    private String dateBeg;

    private String dateEnd;
}
