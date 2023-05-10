package org.ktfoms.med.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.ktfoms.med.form.EditFundingFapForm;


@Getter
@Setter
@Entity
@Table(name = "fap_fin")
public class FapFin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fap_fin_id")
    private Integer id;

    @Column(name = "podr")
    private String podr;
//    unique references fap (podr),

    @Column(name = "g_fin1")
    private double gFin1;
//    money not null default 0,

    @Column(name = "k_ykomp1")
    private double kYkomp1;
//    numeric(3,2) not null default 0,

    @Column(name = "summ_astra1")
    private double summAstra1;
//    money not null default 0,

    @Column(name = "summ_kapit1")
    private double summKapit1;
//    money not null default 0,

    //block 2
    @Column(name = "g_fin2")
    private double gFin2;
    @Column(name = "k_ykomp2")
    private double kYkomp2;
    @Column(name = "summ_astra2")
    private double summAstra2;
    @Column(name = "summ_kapit2")
    private double summKapit2;

    //block 3
    @Column(name = "g_fin3")
    private double gFin3;
    @Column(name = "k_ykomp3")
    private double kYkomp3;
    @Column(name = "summ_astra3")
    private double summAstra3;
    @Column(name = "summ_kapit3")
    private double summKapit3;

    //block 4
    @Column(name = "g_fin4")
    private double gFin4;
    @Column(name = "k_ykomp4")
    private double kYkomp4;
    @Column(name = "summ_astra4")
    private double summAstra4;
    @Column(name = "summ_kapit4")
    private double summKapit4;

    //block 5
    @Column(name = "g_fin5")
    private double gFin5;
    @Column(name = "k_ykomp5")
    private double kYkomp5;
    @Column(name = "summ_astra5")
    private double summAstra5;
    @Column(name = "summ_kapit5")
    private double summKapit5;

    //block 6
    @Column(name = "g_fin6")
    private double gFin6;
    @Column(name = "k_ykomp6")
    private double kYkomp6;
    @Column(name = "summ_astra6")
    private double summAstra6;
    @Column(name = "summ_kapit6")
    private double summKapit6;

    //block 7
    @Column(name = "g_fin7")
    private double gFin7;
    @Column(name = "k_ykomp7")
    private double kYkomp7;
    @Column(name = "summ_astra7")
    private double summAstra7;
    @Column(name = "summ_kapit7")
    private double summKapit7;

    //block 8
    @Column(name = "g_fin8")
    private double gFin8;
    @Column(name = "k_ykomp8")
    private double kYkomp8;
    @Column(name = "summ_astra8")
    private double summAstra8;
    @Column(name = "summ_kapit8")
    private double summKapit8;

    //block 9
    @Column(name = "g_fin9")
    private double gFin9;
    @Column(name = "k_ykomp9")
    private double kYkomp9;
    @Column(name = "summ_astra9")
    private double summAstra9;
    @Column(name = "summ_kapit9")
    private double summKapit9;

    //block 10
    @Column(name = "g_fin10")
    private double gFin10;
    @Column(name = "k_ykomp10")
    private double kYkomp10;
    @Column(name = "summ_astra10")
    private double summAstra10;
    @Column(name = "summ_kapit10")
    private double summKapit10;

    //block 11
    @Column(name = "g_fin11")
    private double gFin11;
    @Column(name = "k_ykomp11")
    private double kYkomp11;
    @Column(name = "summ_astra11")
    private double summAstra11;
    @Column(name = "summ_kapit11")
    private double summKapit11;

    //block 12
    @Column(name = "g_fin12")
    private double gFin12;
    @Column(name = "k_ykomp12")
    private double kYkomp12;
    @Column(name = "summ_astra12")
    private double summAstra12;
    @Column(name = "summ_kapit12")
    private double summKapit12;

    @Column(name = "year")
    private Integer year;


    public void fillMonth(int month) {

        switch(month){
            case 2:
                this.gFin2 = this.gFin1;
                this.kYkomp2 = this.kYkomp1;
                this.summAstra2 = this.summAstra1;
                this.summKapit2 = this.summKapit1;
                break;
            case 3:
                this.gFin3 = this.gFin2;
                this.kYkomp3 = this.kYkomp2;
                this.summAstra3 = this.summAstra2;
                this.summKapit3 = this.summKapit2;
                break;
            case 4:
                this.gFin4 = this.gFin3;
                this.kYkomp4 = this.kYkomp3;
                this.summAstra4 = this.summAstra3;
                this.summKapit4 = this.summKapit3;
                break;
            case 5:
                this.gFin5 = this.gFin4;
                this.kYkomp5 = this.kYkomp4;
                this.summAstra5 = this.summAstra4;
                this.summKapit5 = this.summKapit4;
                break;
            case 6:
                this.gFin6 = this.gFin5;
                this.kYkomp6 = this.kYkomp5;
                this.summAstra6 = this.summAstra5;
                this.summKapit6 = this.summKapit5;
                break;
            case 7:
                this.gFin7 = this.gFin6;
                this.kYkomp7 = this.kYkomp6;
                this.summAstra7 = this.summAstra6;
                this.summKapit7 = this.summKapit6;
                break;
            case 8:
                this.gFin8 = this.gFin7;
                this.kYkomp8 = this.kYkomp7;
                this.summAstra8 = this.summAstra7;
                this.summKapit8 = this.summKapit7;
                break;
            case 9:
                this.gFin9 = this.gFin8;
                this.kYkomp9 = this.kYkomp8;
                this.summAstra9 = this.summAstra8;
                this.summKapit9 = this.summKapit8;
                break;
            case 10:
                this.gFin10 = this.gFin9;
                this.kYkomp10 = this.kYkomp9;
                this.summAstra10 = this.summAstra9;
                this.summKapit10 = this.summKapit9;
                break;
            case 11:
                this.gFin11 = this.gFin10;
                this.kYkomp11 = this.kYkomp10;
                this.summAstra11 = this.summAstra10;
                this.summKapit11 = this.summKapit10;
                break;
            case 12:
                this.gFin12 = this.gFin11;
                this.kYkomp12 = this.kYkomp11;
                this.summAstra12 = this.summAstra11;
                this.summKapit12 = this.summKapit11;
                break;
        }
    }


    public void editByForm(EditFundingFapForm form) {
        setGFin1(Double.parseDouble(form.getGFin1().replace(',', '.')));
        setKYkomp1(Double.parseDouble(form.getKYkomp1().replace(',', '.')));
        setSummAstra1(Double.parseDouble(form.getSummAstra1().replace(',', '.')));
        setSummKapit1(Double.parseDouble(form.getSummKapit1().replace(',', '.')));

        setGFin2(Double.parseDouble(form.getGFin2().replace(',', '.')));
        setKYkomp2(Double.parseDouble(form.getKYkomp2().replace(',', '.')));
        setSummAstra2(Double.parseDouble(form.getSummAstra2().replace(',', '.')));
        setSummKapit2(Double.parseDouble(form.getSummKapit2().replace(',', '.')));

        setGFin3(Double.parseDouble(form.getGFin3().replace(',', '.')));
        setKYkomp3(Double.parseDouble(form.getKYkomp3().replace(',', '.')));
        setSummAstra3(Double.parseDouble(form.getSummAstra3().replace(',', '.')));
        setSummKapit3(Double.parseDouble(form.getSummKapit3().replace(',', '.')));

        setGFin4(Double.parseDouble(form.getGFin4().replace(',', '.')));
        setKYkomp4(Double.parseDouble(form.getKYkomp4().replace(',', '.')));
        setSummAstra4(Double.parseDouble(form.getSummAstra4().replace(',', '.')));
        setSummKapit4(Double.parseDouble(form.getSummKapit4().replace(',', '.')));

        setGFin5(Double.parseDouble(form.getGFin5().replace(',', '.')));
        setKYkomp5(Double.parseDouble(form.getKYkomp5().replace(',', '.')));
        setSummAstra5(Double.parseDouble(form.getSummAstra5().replace(',', '.')));
        setSummKapit5(Double.parseDouble(form.getSummKapit5().replace(',', '.')));

        setGFin6(Double.parseDouble(form.getGFin6().replace(',', '.')));
        setKYkomp6(Double.parseDouble(form.getKYkomp6().replace(',', '.')));
        setSummAstra6(Double.parseDouble(form.getSummAstra6().replace(',', '.')));
        setSummKapit6(Double.parseDouble(form.getSummKapit6().replace(',', '.')));

        setGFin7(Double.parseDouble(form.getGFin7().replace(',', '.')));
        setKYkomp7(Double.parseDouble(form.getKYkomp7().replace(',', '.')));
        setSummAstra7(Double.parseDouble(form.getSummAstra7().replace(',', '.')));
        setSummKapit7(Double.parseDouble(form.getSummKapit7().replace(',', '.')));

        setGFin8(Double.parseDouble(form.getGFin8().replace(',', '.')));
        setKYkomp8(Double.parseDouble(form.getKYkomp8().replace(',', '.')));
        setSummAstra8(Double.parseDouble(form.getSummAstra8().replace(',', '.')));
        setSummKapit8(Double.parseDouble(form.getSummKapit8().replace(',', '.')));

        setGFin9(Double.parseDouble(form.getGFin9().replace(',', '.')));
        setKYkomp9(Double.parseDouble(form.getKYkomp9().replace(',', '.')));
        setSummAstra9(Double.parseDouble(form.getSummAstra9().replace(',', '.')));
        setSummKapit9(Double.parseDouble(form.getSummKapit9().replace(',', '.')));

        setGFin10(Double.parseDouble(form.getGFin10().replace(',', '.')));
        setKYkomp10(Double.parseDouble(form.getKYkomp10().replace(',', '.')));
        setSummAstra10(Double.parseDouble(form.getSummAstra10().replace(',', '.')));
        setSummKapit10(Double.parseDouble(form.getSummKapit10().replace(',', '.')));

        setGFin11(Double.parseDouble(form.getGFin11().replace(',', '.')));
        setKYkomp11(Double.parseDouble(form.getKYkomp11().replace(',', '.')));
        setSummAstra11(Double.parseDouble(form.getSummAstra11().replace(',', '.')));
        setSummKapit11(Double.parseDouble(form.getSummKapit11().replace(',', '.')));

        setGFin12(Double.parseDouble(form.getGFin12().replace(',', '.')));
        setKYkomp12(Double.parseDouble(form.getKYkomp12().replace(',', '.')));
        setSummAstra12(Double.parseDouble(form.getSummAstra12().replace(',', '.')));
        setSummKapit12(Double.parseDouble(form.getSummKapit12().replace(',', '.')));

    }
}
