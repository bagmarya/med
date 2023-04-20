package org.ktfoms.med.dao;

import org.hibernate.SessionFactory;
import org.ktfoms.med.entity.Lpu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LpuDao {

    private static final Logger logger = LoggerFactory.getLogger(LpuDao.class);

    private SessionFactory sessionFactory;

    public LpuDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Lpu getById(Integer id) {
//        return sessionFactory.getCurrentSession().get(Lpu.class, id);
        return sessionFactory.getCurrentSession().createQuery("select l from Lpu l where l.id = :id", Lpu.class)
                .setParameter("id", id).getSingleResult();
    }


    public String gettime () {
        String res = sessionFactory.getCurrentSession().createQuery("select 'NOW';", String.class)
                .getSingleResult();
        System.out.println(res);
        return res;
    }

}
