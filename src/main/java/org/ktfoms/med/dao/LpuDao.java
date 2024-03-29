package org.ktfoms.med.dao;

import org.hibernate.SessionFactory;
import org.ktfoms.med.dto.FundingNormaDto;
import org.ktfoms.med.entity.FundingNorma;
import org.ktfoms.med.entity.FundingNormaSmp;
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
        return sessionFactory.getCurrentSession().createQuery("select l from Lpu l  where l.dateEnd is null and l.mkod!=450000 order by l.mkod", Lpu.class)
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
    @Transactional
    public List<FundingNorma> getFundingNormaEntityList(LocalDate startDate, LocalDate endDate) {
        return sessionFactory.getCurrentSession().createQuery("select fn from FundingNorma fn " +
                        "where fn.fundingDate between :startDate and :endDate " +
                        "order by fn.fundingDate desc,fn.mNameF ", FundingNorma.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
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

    public List<FundingNormaDto> getFundingNormaDtoListByDate(LocalDate date) {
        return sessionFactory.getCurrentSession().createQuery("select new org.ktfoms.med.dto.FundingNormaDto(" +
                        "l.mNameS, l.cogrn, fn.fundingDate, fn.quantityInAstr, fn.quantityInKap, fn.norma) " +
                        "from FundingNorma fn join Lpu l on fn.lpuId = l.id where fn.fundingDate = :date", FundingNormaDto.class)
                .setParameter("date", date).getResultList();
    }

    public List<FundingNormaSmp> getFundingNormaSmpEntityList(Integer year) {
        return sessionFactory.getCurrentSession().createQuery("select fns from FundingNormaSmp fns " +
                        "where EXTRACT(YEAR FROM fns.datebeg) = :year " +
                        "order by fns.datebeg, fns.mcod, fns.smo ", FundingNormaSmp.class)
                .setParameter("year", year)
                .getResultList();
    }

    public List<FundingNormaSmp> getFundingNormaSmpEntityList(LocalDate date) {
        return sessionFactory.getCurrentSession().createQuery("select fns from FundingNormaSmp fns " +
                        "where fns.datebeg = :date " +
                        "order by fns.mcod, fns.smo ", FundingNormaSmp.class)
                .setParameter("date", date)
                .getResultList();
    }

    public List<FundingNormaSmp> getFundingNormaSmpEntityList() {
        return sessionFactory.getCurrentSession().createQuery("select fns from FundingNormaSmp fns " +
                        "order by fns.datebeg, fns.mcod, fns.smo ", FundingNormaSmp.class)
                .getResultList();
    }

    public List<FundingNormaSmp> getFundingNormaSmpEntityList(LocalDate from, LocalDate to) {
        return sessionFactory.getCurrentSession().createQuery("select fns from FundingNormaSmp fns " +
                        "where fns.datebeg <= :to and fns.dateend >= :from ", FundingNormaSmp.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
    }

    public List<FundingNormaSmp> getFundingNormaSmpEntityList(int mcod, LocalDate datebeg, LocalDate dateend) {
        return sessionFactory.getCurrentSession().createQuery("select fns from FundingNormaSmp fns " +
                        "where fns.datebeg = :datebeg and fns.dateend = :dateend and fns.mcod = :mcod ", FundingNormaSmp.class)
                .setParameter("datebeg", datebeg)
                .setParameter("dateend", dateend)
                .setParameter("mcod", mcod)
                .getResultList();
    }

    public void clearFundingNormaSmp(LocalDate date) {
        sessionFactory.getCurrentSession().createQuery("delete from FundingNormaSmp fns " +
                "where fns.datebeg = :date ")
                .setParameter("date", date)
                .executeUpdate();
    }

    public List<Integer> getMcodeList() {
        return sessionFactory.getCurrentSession().createQuery("select l.mkod from Lpu l", Integer.class)
                .getResultList();
    }

    public Lpu getLpuByMcod(Integer mcod) {
        return sessionFactory.getCurrentSession().createQuery("select l from Lpu l where l.mkod = :mcod", Lpu.class)
                .setParameter("mcod", mcod)
                .getSingleResult();
    }

    public List<String> getOidList() {
        return sessionFactory.getCurrentSession().createQuery("select l.kodSp from Lpu l", String.class)
                .getResultList();
    }
}
