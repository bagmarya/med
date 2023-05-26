package org.ktfoms.med.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.ktfoms.med.entity.User;

@org.springframework.stereotype.Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {


    public User findByUsername(String username);
//    {
//        return sessionFactory.getCurrentSession().createQuery("select u from User u where u.name = :username", User.class)
//                .setParameter("username", username).getSingleResult();
//    }
}
