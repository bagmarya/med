package org.ktfoms.med.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FundingNormaSmpInfo {

    private String lpuName;
    private Integer mcod;
    private String datebeg;
    private String dateend;
    private Integer kolZlAstr;
    private Integer kolZlKapit;
    private Double tarif;

}
