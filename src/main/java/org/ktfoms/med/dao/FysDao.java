package org.ktfoms.med.dao;


import org.hibernate.SessionFactory;
import org.ktfoms.med.entity.Fys;
import org.ktfoms.med.entity.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class FysDao {

    private static final Logger logger = LoggerFactory.getLogger(FysDao.class);
    private SessionFactory sessionFactory;

    public FysDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Fys getById(int id) {
        return sessionFactory.getCurrentSession().get(Fys.class, id);
    }


    public void save(Object object) {
        if (object == null) {
            return;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    public List<Fys> getFysEntityList() {
        return sessionFactory.getCurrentSession().createQuery("select fys from Fys fys order by fys.kodSp desc", Fys.class)
                .getResultList();
    }

    public void clearFys() {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM Fys").executeUpdate();
    }

    public List<Price> getPriceEntityList() {
        return sessionFactory.getCurrentSession().createQuery("select p from Price p", Price.class)
                .getResultList();
    }

    public List<Price> getObrcPriceEntityList() {
        return sessionFactory.getCurrentSession().createQuery("select p from Price p where p.mkr = :mkr order by p.kod", Price.class)
                .setParameter("mkr", "ОБ").getResultList();
    }

    public void clearPrice() {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM Price").executeUpdate();
    }

    public void fillSpezCode() {
        sessionFactory.getCurrentSession().createNativeQuery("insert into spez_code (select distinct code, spez from price p order by code)").executeUpdate();
    }

    public void clearSpezCode() {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM Spez").executeUpdate();
    }
}
