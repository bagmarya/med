package org.ktfoms.med.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FundingNormaDto {
    private String mNameS;
    private Long ogrn;
    private LocalDate fundingDate;
    private Integer quantityInAstr;
    private Integer quantityInKap;
    private Double norma;
    public String getFormatedFundingDate(){
        return this.fundingDate.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
    }
}
