package org.ktfoms.med.entity;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.ktfoms.med.dto.LpuF003Dto;
import org.ktfoms.med.dto.LpuF032Dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "lpu")
public class Lpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lpu_id")
    private Integer id;

    @Column(name = "c_ogrn")
    private Long cogrn;

    @Column(name = "tf_okato")
    private  Integer tfokato;

    @Column(name = "m_names")
    private  String mNameS;

    @Column(name = "m_namef")
    private  String mNameF;

    @Column(name = "kpp")
    private  Integer kpp;

    @Column(name = "okved")
    private  Integer okved;

    @Column(name = "post_id")
    private  Integer postId;

    @Column(name = "area_name")
    private  String areaName;

    @Column(name = "r_name")
    private  String rName;

    @Column(name = "np_name")
    private  String npName;

    @Column(name = "ul_name")
    private  String ulName;

    @Column(name = "dom")
    private  String dom;

    @Column(name = "gvfio")
    private  String gvfio;

    @Column(name = "tel")
    private  String tel;

    @Column(name = "fax")
    private  String fax;

    @Column(name = "e_mail")
    private  String eMail;

    @Column(name = "lpuinn")
    private Long lpuinn;

    @Column(name = "n_svod")
    private  Integer nSvod;

    @Column(name = "mkod")
    private  Integer mkod;

    @Column(name = "kod_sp")
    private  String kodSp;

    @Column(name = "funded")
    private boolean funded;

    @Column(name = "date_beg")
    private LocalDate dateBeg;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    public Lpu() {
    }

    public Lpu(LpuF003Dto dto){
        this.cogrn = dto.getOgrn();
        this.tfokato = 37;
        this.mNameS = dto.getMNameS();
        this.mNameF = dto.getMNameF();
        this.kpp = dto.getKpp();
        this.postId = dto.getPostId();
        this.gvfio = dto.getFamRuk() + " " + dto.getImRuk() + " " + dto.getOtRuk();
        this.tel = dto.getTel();
        this.fax = dto.getFax();
        this.eMail = dto.getEMail();
        this.lpuinn = dto.getLpuinn();
        this.mkod = dto.getMcod();
        this.dateBeg = LocalDate.parse(dto.getDateBeg(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        if (!Objects.equals(dto.getDateEnd(), "")){this.dateEnd = LocalDate.parse(dto.getDateEnd(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));}

    }

    public Lpu(Integer id,
               Long cogrn,
               Integer tfokato,
               String mNameS,
               String mNameF,
               Integer kpp,
               Integer okved,
               Integer postId,
               String areaName,
               String rName,
               String npName,
               String ulName,
               String dom,
               String gvfio,
               String tel,
               String fax,
               String eMail,
               Long lpuinn,
               Integer nSvod,
               Integer mkod,
               String kodSp) {
        this.id = id;
        this.cogrn = cogrn;
        this.tfokato = tfokato;
        this.mNameS = mNameS;
        this.mNameF = mNameF;
        this.kpp = kpp;
        this.okved = okved;
        this.postId = postId;
        this.areaName = areaName;
        this.rName = rName;
        this.npName = npName;
        this.ulName = ulName;
        this.dom = dom;
        this.gvfio = gvfio;
        this.tel = tel;
        this.fax = fax;
        this.eMail = eMail;
        this.lpuinn = lpuinn;
        this.nSvod = nSvod;
        this.mkod = mkod;
        this.kodSp = kodSp;
    }

    public Lpu(LpuF032Dto dto) {
        this.cogrn = dto.getOgrn();
        this.tfokato = 37;
        this.mNameS = dto.getMNameS();
        this.mNameF = dto.getMNameF();
        this.kpp = dto.getKpp();
        this.postId = dto.getPostId();
        this.tel = dto.getTel();
        this.fax = dto.getFax();
        this.eMail = dto.getEMail();
        this.lpuinn = dto.getLpuinn();
        this.mkod = dto.getMcod();
        this.kodSp = dto.getOid();
        this.dateBeg = LocalDate.parse(dto.getDateBeg(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        if (!Objects.equals(dto.getDateEnd(), "")){this.dateEnd = LocalDate.parse(dto.getDateEnd(), DateTimeFormatter.ofPattern("dd.MM.uuuu"));}
        ArrayList<String> adr = new ArrayList<>(List.of(dto.getAddress().split(",")));
        String str = adr.remove(adr.size()-1);
        if(Objects.equals(str.trim(), "")){
            this.dom = adr.remove(adr.size()-1);
        } else {
            this.dom = str;
        }
        this.ulName = adr.remove(adr.size()-1);
        this.npName = adr.remove(adr.size()-1);
        if(adr.get(adr.size()-1).contains("р-н")){this.rName = adr.remove(adr.size()-1);}
        if(adr.get(adr.size()-1).length()>6){this.areaName = adr.remove(adr.size()-1);};
    }
}
