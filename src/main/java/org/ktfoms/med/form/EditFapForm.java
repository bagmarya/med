package org.ktfoms.med.form;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ktfoms.med.entity.Fap;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EditFapForm {

    private final List<String> namesTipePodr = List.of("Амбулаторный");
    private final List<String> namesVidPodr = List.of("Фельдшерско-акушерские пункты (включая передвижные)"
            , "Фельдшерские пункты (включая передвижные)");

    private String moLpu;
    private String podr;
    private String dateN;
    private String dateLik;
    private String namePodr;
    private String dnLicen;
    private String dkLicen;
    private Integer kodTipePodr;
    private String nameTipePodr;
    private Integer kodVidPodr;
    private String nameVidPodr;

    public EditFapForm(Fap entity){
        this.setMoLpu(entity.getMoLpu());
        this.setPodr(entity.getPodr());
        if (entity.getDateN() != null) {this.setDateN(entity.getDateN().toString());}
        if (entity.getDateLik() != null) {this.setDateLik(entity.getDateLik().toString());}
        this.setNamePodr(entity.getNamePodr());
        if (entity.getDnLicen() != null) {this.setDnLicen(entity.getDnLicen().toString());}
        if (entity.getDkLicen() != null) {this.setDkLicen(entity.getDkLicen().toString());}
        this.setKodTipePodr(entity.getKodTipePodr());
        this.setNameTipePodr(entity.getNameTipePodr());
        this.setKodVidPodr(entity.getKodVidPodr());
        this.setNameVidPodr(entity.getNameVidPodr());
    }
}
