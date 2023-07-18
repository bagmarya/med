package org.ktfoms.med.dao;

import org.hibernate.SessionFactory;
import org.ktfoms.med.entity.Profil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
