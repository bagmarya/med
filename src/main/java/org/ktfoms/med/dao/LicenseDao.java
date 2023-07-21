package org.ktfoms.med.dao;

import jakarta.persistence.Tuple;
import org.hibernate.SessionFactory;
import org.ktfoms.med.entity.LicensePol;
import org.ktfoms.med.entity.LicenseStac;
import org.ktfoms.med.entity.Profil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional
public class LicenseDao {
    private static final Logger logger = LoggerFactory.getLogger(LicenseDao.class);
    private SessionFactory sessionFactory;

    public LicenseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Profil getById(int id) {
        return sessionFactory.getCurrentSession().get(Profil.class, id);
    }

    public void save(Object object) {
        if (object == null) {
            return;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    public void clearProfil() {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM Profil").executeUpdate();
    }

    public List<LicenseStac> getLicenseStacList() {
        return sessionFactory.getCurrentSession()
                .createQuery("select ls from LicenseStac ls order by ls.mcod, ls.stacType, ls.dateBeg", LicenseStac.class)
                .getResultList();
    }
    public List<LicenseStac> getLicenseStacList(Integer mcod) {
        return sessionFactory.getCurrentSession()
                .createQuery("select ls from LicenseStac ls where ls.mcod=:mcod order by ls.stacType, ls.dateBeg", LicenseStac.class)
                .setParameter("mcod", mcod)
                .getResultList();
    }
    public List<LicensePol> getLicensePolList() {
        return sessionFactory.getCurrentSession()
                .createQuery("select lp from LicensePol lp order by lp.mcod, lp.spez, lp.dateBeg", LicensePol.class)
                .getResultList();
    }

    public List<LicensePol> getLicensePolList(Integer mcod) {
        return sessionFactory.getCurrentSession()
                .createQuery("select lp from LicensePol lp where ls.mcod=:mcod order by lp.dateBeg", LicensePol.class)
                .getResultList();
    }

    public Map<String, String> getAgeMap() {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select age_code, age_group from age_group;", Tuple.class)
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> tuple.get("age_code").toString(),
                                tuple -> tuple.get("age_group").toString()
                        ));
    }

    public Map<Integer, String> getProfilMap() {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select idpr, prname from sp_v002_profil;", Tuple.class)
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((Number) tuple.get("idpr")).intValue(),
                                tuple -> tuple.get("prname").toString()
                        ));
    }

    public Map<Integer, String> getStacTypeMap() {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select stac_type_code, stac_type_name from stac_type;", Tuple.class)
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((Number) tuple.get("stac_type_code")).intValue(),
                                tuple -> tuple.get("stac_type_name").toString()
                        ));
    }

    public Map<Integer, String> getPayTypeMap() {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select pay_type_code, pay_type_name from pay_type;", Tuple.class)
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> ((Number) tuple.get("pay_type_code")).intValue(),
                                tuple -> tuple.get("pay_type_name").toString()
                        ));
    }

    public Map<String, String> getSpezMap() {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select code, spez from spez_code;", Tuple.class)
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> tuple.get("code").toString(),
                                tuple -> tuple.get("spez").toString()
                        ));
    }

    public Map<String, String> getCategoryMap() {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select category_code, category_name from medical_category;", Tuple.class)
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                tuple -> tuple.get("category_code").toString(),
                                tuple -> tuple.get("category_name").toString()
                        ));
    }

    public LicenseStac getLicenseStacById(Integer id) {
            return sessionFactory.getCurrentSession().get(LicenseStac.class, id);
        }
}
