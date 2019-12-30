/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.DAO.implementation;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import njt.supplier.SupplierApp.DAO.DobavljacDAO;
import njt.supplier.SupplierApp.entity.Dobavljac;
import njt.supplier.SupplierApp.entity.Prenociste;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ivan
 */
@Repository
public class DobavljacDAOImpl implements DobavljacDAO {

    private EntityManager entityManager;

    @Autowired
    public DobavljacDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<Dobavljac> getAllDobavljaci() {
        Session session = entityManager.unwrap(Session.class);

        Query<Dobavljac> query = session.createQuery("from Dobavljac", Dobavljac.class);

        List<Dobavljac> dobavljaci = query.getResultList();

        return dobavljaci;
    }

    @Override
    public Dobavljac getDobavljacPrekoID(int idDobavljaca) {
        Session session = entityManager.unwrap(Session.class);

        Query<Dobavljac> query = session.createQuery("from Dobavljac D where D.id=:idDobavljaca", Dobavljac.class).setParameter("idDobavljaca", idDobavljaca);

        Dobavljac dobavljac = query.getSingleResult();

        return dobavljac;
    }

}
