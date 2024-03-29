package org.ktfoms.med.dao;

import org.hibernate.SessionFactory;
import org.ktfoms.med.dto.FapDto;
import org.ktfoms.med.dto.FapFinDto;
import org.ktfoms.med.dto.LpuFapCountDto;
import org.ktfoms.med.entity.Fap;
import org.ktfoms.med.entity.FapFin;
import org.ktfoms.med.entity.Lpu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class FapDao {
    private static final Logger logger = LoggerFactory.getLogger(FapDao.class);
    private SessionFactory sessionFactory;

    public FapDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Fap getById(int id) {
        return sessionFactory.getCurrentSession().get(Fap.class, id);
    }


    @Transactional
    public FapDto getFapDtoById(Integer id) {
        return sessionFactory.getCurrentSession().createQuery("select new org.ktfoms.med.dto.FapDto(" +
                        "f.moLpu, f.podr, f.dateN, f.dateLik, f.namePodr, " +
                        "f.dnLicen, f.dkLicen, f.kodTipePodr, f.nameTipePodr," +
                        " f.kodVidPodr, f.nameVidPodr, l.mkod) " +
                        "from Fap f join Lpu l on f.moLpu = l.kodSp where f.id = :id", FapDto.class)
                .setParameter("id", id).getSingleResult();
    }

    @Transactional
    public List<Fap> getFapEntityList() {
        return sessionFactory.getCurrentSession().createQuery("select f from Fap f order by f.podr", Fap.class)
                .getResultList();
    }

    public List<Fap> getFapEntityListByLpu(String lpu) {
        return sessionFactory.getCurrentSession().createQuery("select f from Fap f where f.moLpu =:lpu order by f.podr", Fap.class)
                .setParameter("lpu", lpu).getResultList();
    }

    @Transactional
    public FapFin getFapFinById(int id) {
        return sessionFactory.getCurrentSession().get(FapFin.class, id);
    }


    @Transactional
    public List<FapFinDto> getFapFinDtoList(Integer year) {
        return sessionFactory.getCurrentSession().createQuery("select new org.ktfoms.med.dto.FapFinDto(" +
                        "l.mkod, f.namePodr, f.moLpu, f.podr, " +
                        "ff.gFin1, ff.kYkomp1, ff.summAstra1, ff.summKapit1," +
                        "ff.gFin2, ff.kYkomp2, ff.summAstra2, ff.summKapit2," +
                        "ff.gFin3, ff.kYkomp3, ff.summAstra3, ff.summKapit3," +
                        "ff.gFin4, ff.kYkomp4, ff.summAstra4, ff.summKapit4," +
                        "ff.gFin5, ff.kYkomp5, ff.summAstra5, ff.summKapit5," +
                        "ff.gFin6, ff.kYkomp6, ff.summAstra6, ff.summKapit6," +
                        "ff.gFin7, ff.kYkomp7, ff.summAstra7, ff.summKapit7," +
                        "ff.gFin8, ff.kYkomp8, ff.summAstra8, ff.summKapit8," +
                        "ff.gFin9, ff.kYkomp9, ff.summAstra9, ff.summKapit9," +
                        "ff.gFin10, ff.kYkomp10, ff.summAstra10, ff.summKapit10," +
                        "ff.gFin11, ff.kYkomp11, ff.summAstra11, ff.summKapit11," +
                        "ff.gFin12, ff.kYkomp12, ff.summAstra12, ff.summKapit12) " +
                        "from Fap f join Lpu l on f.moLpu = l.kodSp join FapFin ff on f.podr = ff.podr " +
                        "where ff.year = :year order by f.namePodr", FapFinDto.class)
                .setParameter("year", year).getResultList();
    }

    @Transactional
    public List<FapFinDto> getFapFinDtoListByLpu(Integer year, String lpu) {
        return sessionFactory.getCurrentSession().createQuery("select new org.ktfoms.med.dto.FapFinDto(" +
                        "l.mkod, f.namePodr, f.moLpu, f.podr, " +
                        "ff.gFin1, ff.kYkomp1, ff.summAstra1, ff.summKapit1," +
                        "ff.gFin2, ff.kYkomp2, ff.summAstra2, ff.summKapit2," +
                        "ff.gFin3, ff.kYkomp3, ff.summAstra3, ff.summKapit3," +
                        "ff.gFin4, ff.kYkomp4, ff.summAstra4, ff.summKapit4," +
                        "ff.gFin5, ff.kYkomp5, ff.summAstra5, ff.summKapit5," +
                        "ff.gFin6, ff.kYkomp6, ff.summAstra6, ff.summKapit6," +
                        "ff.gFin7, ff.kYkomp7, ff.summAstra7, ff.summKapit7," +
                        "ff.gFin8, ff.kYkomp8, ff.summAstra8, ff.summKapit8," +
                        "ff.gFin9, ff.kYkomp9, ff.summAstra9, ff.summKapit9," +
                        "ff.gFin10, ff.kYkomp10, ff.summAstra10, ff.summKapit10," +
                        "ff.gFin11, ff.kYkomp11, ff.summAstra11, ff.summKapit11," +
                        "ff.gFin12, ff.kYkomp12, ff.summAstra12, ff.summKapit12) " +
                        "from Fap f join Lpu l on f.moLpu = l.kodSp join FapFin ff on f.podr = ff.podr " +
                        "where ff.year = :year and f.moLpu = :lpu order by f.namePodr", FapFinDto.class)
                .setParameter("year", year)
                .setParameter("lpu", lpu)
                .getResultList();
    }



    @Transactional
    public List<FapFin> getFapFinEntityList(Integer year) {
        return sessionFactory.getCurrentSession().createQuery("select ff from FapFin ff where ff.year = :year", FapFin.class)
                .setParameter("year", year).getResultList();
    }

    @Transactional
    public void save(Object object) {
        if (object == null) {
            return;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }


    @Transactional
    public List<LpuFapCountDto> getLpuFapCountDtoList() {
        return sessionFactory.getCurrentSession().createQuery("select " +
                        "new org.ktfoms.med.dto.LpuFapCountDto(f.moLpu, l.mNameS, count(*)) " +
                        "from Fap f join Lpu l on f.moLpu = l.kodSp " +
                        "where f.dateLik is null " +
                        "group by f.moLpu, l.mNameS " +
                        "order by l.mNameS", LpuFapCountDto.class)
                .getResultList();
    }

    public FapFinDto getFapFinDtoByPodrYear(String podr, String year) {
        return sessionFactory.getCurrentSession().createQuery("select new org.ktfoms.med.dto.FapFinDto(" +
                        "l.mkod, f.namePodr, f.moLpu, f.podr, " +
                        "ff.gFin1, ff.kYkomp1, ff.summAstra1, ff.summKapit1," +
                        "ff.gFin2, ff.kYkomp2, ff.summAstra2, ff.summKapit2," +
                        "ff.gFin3, ff.kYkomp3, ff.summAstra3, ff.summKapit3," +
                        "ff.gFin4, ff.kYkomp4, ff.summAstra4, ff.summKapit4," +
                        "ff.gFin5, ff.kYkomp5, ff.summAstra5, ff.summKapit5," +
                        "ff.gFin6, ff.kYkomp6, ff.summAstra6, ff.summKapit6," +
                        "ff.gFin7, ff.kYkomp7, ff.summAstra7, ff.summKapit7," +
                        "ff.gFin8, ff.kYkomp8, ff.summAstra8, ff.summKapit8," +
                        "ff.gFin9, ff.kYkomp9, ff.summAstra9, ff.summKapit9," +
                        "ff.gFin10, ff.kYkomp10, ff.summAstra10, ff.summKapit10," +
                        "ff.gFin11, ff.kYkomp11, ff.summAstra11, ff.summKapit11," +
                        "ff.gFin12, ff.kYkomp12, ff.summAstra12, ff.summKapit12) " +
                        "from Fap f join Lpu l on f.moLpu = l.kodSp join FapFin ff on f.podr = ff.podr " +
                        "where ff.year = :year and f.podr = :podr", FapFinDto.class)
                .setParameter("year", year)
                .setParameter("podr", podr)
                .getSingleResult();
    }

    public FapFin getFapFinByPodrYear(String podr, Integer year) {
        return sessionFactory.getCurrentSession().createQuery("select ff from FapFin ff where ff.year = :year and ff.podr = :podr", FapFin.class)
                .setParameter("year", year)
                .setParameter("podr", podr)
                .getSingleResult();
    }


    public void deleteFinFapForYear(Integer year) {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM FapFin ff where ff.year = :year")
                .setParameter("year", year)
                .executeUpdate();
    }
}
