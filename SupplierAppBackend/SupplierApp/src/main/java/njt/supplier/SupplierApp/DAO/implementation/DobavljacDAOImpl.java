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
    public List<Dobavljac> getAllDobavljaci() {
        try {

            Session session = entityManager.unwrap(Session.class);

            Query<Dobavljac> query = session.createQuery("from Dobavljac", Dobavljac.class);

            List<Dobavljac> dobavljaci = query.getResultList();

            return dobavljaci;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Dobavljac getDobavljacPrekoID(int idDobavljaca) {
        try {
            Session session = entityManager.unwrap(Session.class);

            Query<Dobavljac> query = session.createQuery("from Dobavljac D where D.id=:idDobavljaca", Dobavljac.class).setParameter("idDobavljaca", idDobavljaca);

            Dobavljac dobavljac = query.getSingleResult();

            return dobavljac;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Dobavljac insertDobavljac(Dobavljac dobavljac) {

        try {
            Session session = entityManager.unwrap(Session.class);

            session.save(dobavljac);

            return dobavljac;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Dobavljac removeDobavljacByID(int id) {
        try {
            Session session = entityManager.unwrap(Session.class);

            Dobavljac dobavljac = getDobavljacPrekoID(id);

            if (dobavljac != null) {
                try {
                    session.delete(dobavljac);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            return dobavljac;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Dobavljac patchDobavljac(int id, Dobavljac dobavljacNew) {
        try {
            Session session = entityManager.unwrap(Session.class);
            Dobavljac dobavljac = getDobavljacPrekoID(id);

            if (dobavljac != null) {
                dobavljac.setNaziv(dobavljacNew.getNaziv());
                dobavljac.setAdresa(dobavljacNew.getAdresa());

                try {
                    session.update(dobavljac);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            return dobavljac;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
