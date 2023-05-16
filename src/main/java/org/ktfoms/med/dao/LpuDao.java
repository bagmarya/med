package org.ktfoms.med.dao;

import org.hibernate.SessionFactory;
import org.ktfoms.med.entity.FundingNorma;
import org.ktfoms.med.entity.Lpu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class LpuDao {
    @Transactional
    public void save(Object object) {
        if (object == null) {
            return;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }
    private static final Logger logger = LoggerFactory.getLogger(LpuDao.class);

    private SessionFactory sessionFactory;

    public LpuDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Lpu getById(Integer id) {
        return sessionFactory.getCurrentSession().get(Lpu.class, id);
    }


    @Transactional
    public List<Lpu> getLpuEntityList() {
        return sessionFactory.getCurrentSession().createQuery("select l from Lpu l order by l.mkod", Lpu.class)
                .getResultList();
    }
    @Transactional
    public List<FundingNorma> getFundingNormaEntityList(LocalDate fundingDate) {
        return sessionFactory.getCurrentSession().createQuery("select fn from FundingNorma fn where fn.fundingDate = :fundingDate", FundingNorma.class)
                .setParameter("fundingDate", fundingDate).getResultList();
    }

    @Transactional
    public List<FundingNorma> getFundingNormaEntityList() {
        return sessionFactory.getCurrentSession().createQuery("select fn from FundingNorma fn order by fn.fundingDate,fn.mNameF", FundingNorma.class)
                .getResultList();
    }

    public FundingNorma getFundingNormaById(int id) {
        return sessionFactory.getCurrentSession().get(FundingNorma.class, id);
    }

    public List<Lpu> getFundedLpuList() {
        return sessionFactory.getCurrentSession().createQuery("select l from Lpu l where l.funded = true", Lpu.class)
                .getResultList();
    }

    public Lpu getLpuByOid(String oid) {
        return sessionFactory.getCurrentSession().createQuery("select l from Lpu l where l.kodSp = :oid", Lpu.class)
                .setParameter("oid", oid).getSingleResult();
    }
}
