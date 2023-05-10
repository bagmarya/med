package org.ktfoms.med.form;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktfoms.med.dto.FapFinDto;

@Getter
@Setter
public class EditFundingFapForm {
    private String gFin1;
    private String kYkomp1;
    private String summAstra1;
    private String summKapit1;

    private String gFin2;
    private String kYkomp2;
    private String summAstra2;
    private String summKapit2;

    private String gFin3;
    private String kYkomp3;
    private String summAstra3;
    private String summKapit3;

    private String gFin4;
    private String kYkomp4;
    private String summAstra4;
    private String summKapit4;

    private String gFin5;
    private String kYkomp5;
    private String summAstra5;
    private String summKapit5;

    private String gFin6;
    private String kYkomp6;
    private String summAstra6;
    private String summKapit6;

    private String gFin7;
    private String kYkomp7;
    private String summAstra7;
    private String summKapit7;

    private String gFin8;
    private String kYkomp8;
    private String summAstra8;
    private String summKapit8;

    private String gFin9;
    private String kYkomp9;
    private String summAstra9;
    private String summKapit9;

    private String gFin10;
    private String kYkomp10;
    private String summAstra10;
    private String summKapit10;

    private String gFin11;
    private String kYkomp11;
    private String summAstra11;
    private String summKapit11;

    private String gFin12;
    private String kYkomp12;
    private String summAstra12;
    private String summKapit12;
    public EditFundingFapForm() {
    }


    public EditFundingFapForm (FapFinDto dto){

        this.gFin1 = String.valueOf(dto.getGFin1());
        this.kYkomp1 = String.valueOf(dto.getKYkomp1());
        this.summAstra1 = String.valueOf(dto.getSummAstra1());
        this.summKapit1 = String.valueOf(dto.getSummKapit1());

        this.gFin2 = String.valueOf(dto.getGFin2());
        this.kYkomp2 = String.valueOf(dto.getKYkomp2());
        this.summAstra2 = String.valueOf(dto.getSummAstra2());
        this.summKapit2 = String.valueOf(dto.getSummKapit2());

        this.gFin3 = String.valueOf(dto.getGFin3());
        this.kYkomp3 = String.valueOf(dto.getKYkomp3());
        this.summAstra3 = String.valueOf(dto.getSummAstra3());
        this.summKapit3 = String.valueOf(dto.getSummKapit3());

        this.gFin4 = String.valueOf(dto.getGFin4());
        this.kYkomp4 = String.valueOf(dto.getKYkomp4());
        this.summAstra4 = String.valueOf(dto.getSummAstra4());
        this.summKapit4 = String.valueOf(dto.getSummKapit4());

        this.gFin5 = String.valueOf(dto.getGFin5());
        this.kYkomp5 = String.valueOf(dto.getKYkomp5());
        this.summAstra5 = String.valueOf(dto.getSummAstra5());
        this.summKapit5 = String.valueOf(dto.getSummKapit5());

        this.gFin6 = String.valueOf(dto.getGFin6());
        this.kYkomp6 = String.valueOf(dto.getKYkomp6());
        this.summAstra6 = String.valueOf(dto.getSummAstra6());
        this.summKapit6 = String.valueOf(dto.getSummKapit6());

        this.gFin7 = String.valueOf(dto.getGFin7());
        this.kYkomp7 = String.valueOf(dto.getKYkomp7());
        this.summAstra7 = String.valueOf(dto.getSummAstra7());
        this.summKapit7 = String.valueOf(dto.getSummKapit7());

        this.gFin8 = String.valueOf(dto.getGFin8());
        this.kYkomp8 = String.valueOf(dto.getKYkomp8());
        this.summAstra8 = String.valueOf(dto.getSummAstra8());
        this.summKapit8 = String.valueOf(dto.getSummKapit8());

        this.gFin9 = String.valueOf(dto.getGFin9());
        this.kYkomp9 = String.valueOf(dto.getKYkomp9());
        this.summAstra9 = String.valueOf(dto.getSummAstra9());
        this.summKapit9 = String.valueOf(dto.getSummKapit9());

        this.gFin10 = String.valueOf(dto.getGFin10());
        this.kYkomp10 = String.valueOf(dto.getKYkomp10());
        this.summAstra10 = String.valueOf(dto.getSummAstra10());
        this.summKapit10 = String.valueOf(dto.getSummKapit10());

        this.gFin11 = String.valueOf(dto.getGFin11());
        this.kYkomp11 = String.valueOf(dto.getKYkomp11());
        this.summAstra11 = String.valueOf(dto.getSummAstra11());
        this.summKapit11 = String.valueOf(dto.getSummKapit11());

        this.gFin12 = String.valueOf(dto.getGFin12());
        this.kYkomp12 = String.valueOf(dto.getKYkomp12());
        this.summAstra12 = String.valueOf(dto.getSummAstra12());
        this.summKapit12 = String.valueOf(dto.getSummKapit12());
    }


}
