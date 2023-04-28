package org.ktfoms.med.dao;

import org.hibernate.SessionFactory;
import org.ktfoms.med.entity.FundingNorma;
import org.ktfoms.med.entity.Lpu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
        return sessionFactory.getCurrentSession().get(Lpu.class, id);
//        return sessionFactory.getCurrentSession().createQuery("select l from Lpu l where l.id = :id", Lpu.class)
//                .setParameter("id", id).getSingleResult();
    }

    @Transactional
    public List<FundingNorma> getFundingNormaEntityList(Date fundingDate) {
        return sessionFactory.getCurrentSession().createQuery("select fn from FundingNorma fn where fn.fundingDate = :fundingDate", FundingNorma.class)
                .setParameter("fundingDate", fundingDate).getResultList();
    }



}
