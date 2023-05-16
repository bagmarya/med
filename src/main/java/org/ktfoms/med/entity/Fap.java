package org.ktfoms.med.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.ktfoms.med.form.EditFapForm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "fap")
public class Fap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fap_id")
    private Integer id;

    @Column(name = "mo_lpu")
    private String moLpu;
//    references lpu (kod_sp),

    @Column(name = "podr")
    private String podr;
//    varchar(50) not null unique,

    @Column(name = "date_n")
    private LocalDate dateN;

    @Column(name = "date_lik")
    private LocalDate dateLik;

    @Column(name = "name_podr")
    private String namePodr;

    @Column(name = "dn_licen")
    private LocalDate dnLicen;

    @Column(name = "dk_licen")
    private LocalDate dkLicen;

    @Column(name = "kod_tipe_podr")
    private Integer kodTipePodr;

    @Column(name = "name_tipe_podr")
    private String nameTipePodr;

    @Column(name = "kod_vid_podr")
    private Integer kodVidPodr;

    @Column(name = "name_vid_podr")
    private String nameVidPodr;

    public Fap() {
    }

    public String getFormatedDateN(){
        if (this.dateN!=null){
        return this.dateN.format(DateTimeFormatter.ofPattern("d.MM.uuuu"));
        }
        else {return "";}
    }

    public String getFormatedDateLik(){
        if (this.dateLik!=null){
        return this.dateLik.format(DateTimeFormatter.ofPattern("d.MM.uuuu"));
        }
        else {return "";}
    }
    public String getFormatedDnLicen(){
        if (this.dnLicen!=null){
        return this.dnLicen.format(DateTimeFormatter.ofPattern("d.MM.uuuu"));
    }
        else {return "";}
    }
    public String getFormatedDkLicen(){
        if (this.dkLicen!=null){
        return this.dkLicen.format(DateTimeFormatter.ofPattern("d.MM.uuuu"));
    }
        else {return "";}
    }

    public void editByForm(EditFapForm form) throws Exception {
        setMoLpu(form.getMoLpu());
        setPodr(form.getPodr());
        setNamePodr(form.getNamePodr());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        if (!Objects.equals(form.getDateN(), "")){setDateN(LocalDate.parse(form.getDateN(),formatter));} else {setDateN(null);}
        if (!Objects.equals(form.getDateLik(), "")){setDateLik(LocalDate.parse(form.getDateLik(),formatter));} else {setDateLik(null);}
        if (!Objects.equals(form.getDnLicen(), "")){setDnLicen(LocalDate.parse(form.getDnLicen(),formatter));} else {setDnLicen(null);}
        if (!Objects.equals(form.getDkLicen(), "")){setDkLicen(LocalDate.parse(form.getDkLicen(),formatter));} else {setDkLicen(null);}


        switch(form.getNameTipePodr()){
            case "Амбулаторный":
                setKodTipePodr(1);
                setNameTipePodr(form.getNameTipePodr());
                break;
            default:
                throw new Exception("Для ФАП не предусмотрен тип " + form.getNameTipePodr());
        }

        switch(form.getNameVidPodr()){
            case "Фельдшерско-акушерские пункты (включая передвижные)":
                setKodVidPodr(1166);
                setNameVidPodr(form.getNameVidPodr());
                break;
            case "Фельдшерские пункты (включая передвижные)":
                setKodVidPodr(1167);
                setNameVidPodr(form.getNameVidPodr());
                break;
            default:
                throw new Exception("Не предусмотрен вид " + form.getNameVidPodr());
        }
    }
}
