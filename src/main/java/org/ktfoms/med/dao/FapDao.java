package org.ktfoms.med.dao;

import org.hibernate.SessionFactory;
import org.ktfoms.med.dto.FapDto;
import org.ktfoms.med.dto.FapFinDto;
import org.ktfoms.med.entity.Fap;
import org.ktfoms.med.entity.FapFin;
import org.ktfoms.med.entity.Lpu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
//        return sessionFactory.getCurrentSession().createQuery("select l from Lpu l where l.id = :id", Lpu.class)
//                .setParameter("id", id).getSingleResult();
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
    public FapFin getFapFinById(int id) {
        return sessionFactory.getCurrentSession().get(FapFin.class, id);
    }


    @Transactional
    public List<FapFinDto> getFapFinDtoList() {
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
                        "from Fap f join Lpu l on f.moLpu = l.kodSp join FapFin ff on f.podr = ff.podr", FapFinDto.class)
                .getResultList();
    }
}
