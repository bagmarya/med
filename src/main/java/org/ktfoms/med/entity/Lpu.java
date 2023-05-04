package org.ktfoms.med.entity;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


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

    public Lpu() {
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
}
