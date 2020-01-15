/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.DAO.implementation;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import njt.supplier.SupplierApp.DAO.UserDAO;
import njt.supplier.SupplierApp.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ivan
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query<User> query = session.createQuery("from User u where u.username =:username", User.class).setParameter("username", username);

            User user = query.getSingleResult();
            System.out.println(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void save(User user) {
        try {
            Session session = entityManager.unwrap(Session.class);

            session.save(user);

        } catch (Exception e) {
        }
    }

}
