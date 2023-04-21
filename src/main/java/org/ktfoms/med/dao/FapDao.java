package org.ktfoms.med.dao;

import org.hibernate.SessionFactory;
import org.ktfoms.med.dto.FapDto;
import org.ktfoms.med.entity.Fap;
import org.ktfoms.med.entity.FapFin;
import org.ktfoms.med.entity.Lpu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    public FapDto getFapDtoById (Integer id) {
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


}
