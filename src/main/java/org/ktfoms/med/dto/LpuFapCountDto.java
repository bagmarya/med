package org.ktfoms.med.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LpuFapCountDto {
    private String moLpu;
    private String NameS;
    private Long fapCount;

    public LpuFapCountDto(String moLpu, String nameS, Long fapCount) {
        this.moLpu = moLpu;
        NameS = nameS;
        this.fapCount = fapCount;
    }
}
