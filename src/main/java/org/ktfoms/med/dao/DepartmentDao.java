package org.ktfoms.med.dao;

import org.hibernate.SessionFactory;
import org.ktfoms.med.entity.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DepartmentDao {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentDao.class);
    private SessionFactory sessionFactory;

    public DepartmentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Department getById(int id) {
        return sessionFactory.getCurrentSession().get(Department.class, id);
    }

    @Transactional
    public void save(Object object) {
        if (object == null) {
            return;
        }
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    public void clearDepartment() {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM Department").executeUpdate();
    }

    public List<Department> getDepartmentListByLpu(String lpu) {
        return sessionFactory.getCurrentSession().createQuery("select d from Department d where d.moLpu =:lpu order by d.podr", Department.class)
                .setParameter("lpu", lpu).getResultList();
    }

    public List<Department> getDepartmentList() {
        return sessionFactory.getCurrentSession().createQuery("select d from Department d order by d.podr", Department.class)
                .getResultList();
    }
}
