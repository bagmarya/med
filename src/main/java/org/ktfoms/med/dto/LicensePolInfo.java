package org.ktfoms.med.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LicensePolInfo {
    private Integer id;

    private Integer mcod;

    private String spez;

    private String category;

    private String age;

    private String dateBeg;

    private String dateEnd;

    private String medSpecV021;
}
