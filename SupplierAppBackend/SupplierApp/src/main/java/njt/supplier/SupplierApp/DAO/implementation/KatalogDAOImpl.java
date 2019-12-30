/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.DAO.implementation;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import njt.supplier.SupplierApp.DAO.KatalogDAO;
import njt.supplier.SupplierApp.entity.Katalog;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ivan
 */
@Repository
public class KatalogDAOImpl implements KatalogDAO {

    EntityManager entityManager;

    @Autowired
    public KatalogDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<Katalog> getAllKatalozi() {

        Session session = entityManager.unwrap(Session.class);

        Query<Katalog> query = session.createQuery("from Katalog", Katalog.class);

        List<Katalog> katalozi = query.getResultList();

        session.close();

        return katalozi;
    }

    @Override
    @Transactional
    public List<Katalog> getAllKataloziZaDobavljaca(int idDobavljaca) {
        Session session = entityManager.unwrap(Session.class);

        Query<Katalog> query = session.createQuery("from Katalog K where K.dobavljac.id= :idDobavljaca", Katalog.class).setParameter("idDobavljaca", idDobavljaca);

        System.out.println("IdDobavljaca" + idDobavljaca);

        System.out.println("Query" + query.getQueryString());

        List<Katalog> katalozi = query.getResultList();

        session.close();

        return katalozi;
    }

    @Override
    @Transactional
    public Katalog getKatalogById(int id) {
        Session session = entityManager.unwrap(Session.class);

        Query<Katalog> query = session.createQuery("from Katalog K where K.id=:idKataloga ", Katalog.class).setParameter("idKataloga", id);
        System.out.println("id" + id);

        System.out.println("Query" + query.getQueryString());
        Katalog katalog = query.getSingleResult();

        

        katalog.getStavke();

        session.close();

        return katalog;
    }

}
