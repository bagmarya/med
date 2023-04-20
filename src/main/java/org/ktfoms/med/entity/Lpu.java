package org.ktfoms.med.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


//@Getter
//@Setter
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

    public Lpu() {
    }

    public Integer getId() {
        return id;
    }

    public Long getCogrn() {
        return cogrn;
    }

    public Integer getTfokato() {
        return tfokato;
    }

    public String getmNameS() {
        return mNameS;
    }

    public String getmNameF() {
        return mNameF;
    }

    public Integer getKpp() {
        return kpp;
    }

    public Integer getOkved() {
        return okved;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getrName() {
        return rName;
    }

    public String getNpName() {
        return npName;
    }

    public String getUlName() {
        return ulName;
    }

    public String getDom() {
        return dom;
    }

    public String getGvfio() {
        return gvfio;
    }

    public String getTel() {
        return tel;
    }

    public String getFax() {
        return fax;
    }

    public String geteMail() {
        return eMail;
    }

    public Long getLpuinn() {
        return lpuinn;
    }

    public Integer getnSvod() {
        return nSvod;
    }

    public Integer getMkod() {
        return mkod;
    }

    public String getKodSp() {
        return kodSp;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCogrn(Long cogrn) {
        this.cogrn = cogrn;
    }

    public void setTfokato(Integer tfokato) {
        this.tfokato = tfokato;
    }

    public void setmNameS(String mNameS) {
        this.mNameS = mNameS;
    }

    public void setmNameF(String mNameF) {
        this.mNameF = mNameF;
    }

    public void setKpp(Integer kpp) {
        this.kpp = kpp;
    }

    public void setOkved(Integer okved) {
        this.okved = okved;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public void setNpName(String npName) {
        this.npName = npName;
    }

    public void setUlName(String ulName) {
        this.ulName = ulName;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public void setGvfio(String gvfio) {
        this.gvfio = gvfio;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setLpuinn(Long lpuinn) {
        this.lpuinn = lpuinn;
    }

    public void setnSvod(Integer nSvod) {
        this.nSvod = nSvod;
    }

    public void setMkod(Integer mkod) {
        this.mkod = mkod;
    }

    public void setKodSp(String kodSp) {
        this.kodSp = kodSp;
    }
}
