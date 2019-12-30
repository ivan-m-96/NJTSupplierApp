/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.DAO.implementation;

import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import njt.supplier.SupplierApp.DAO.PorudzbenicaDAO;
import njt.supplier.SupplierApp.entity.Porudzbenica;
import njt.supplier.SupplierApp.entity.Prenociste;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ivan
 */
@Repository
public class PorudzbenicaDAOImpl implements PorudzbenicaDAO {

    EntityManager entityManager;

    public PorudzbenicaDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Porudzbenica getPorudzbenicaPrekoID(int id) {
        Session session = entityManager.unwrap(Session.class);

        Query<Porudzbenica> query = session.createQuery("from Porudzbenica P where P.id=:porudzbenicaId", Porudzbenica.class).setParameter("porudzbenicaId", id);

        Porudzbenica porudzbenica = query.getSingleResult();

        return porudzbenica;
    }

    @Override
    @Transactional
    public List<Porudzbenica> getAllPorudzbeniceZaDobavljaca(int idDobavljaca) {
        Session session = entityManager.unwrap(Session.class);

        Query<Porudzbenica> query = session.createQuery("from Porudzbenica P where P.dobavljac.id=:idDobavljaca", Porudzbenica.class).setParameter("idDobavljaca", idDobavljaca);

        List<Porudzbenica> porudzbenice = query.getResultList();

        return porudzbenice;
    }

    @Override
    @Transactional
    public Porudzbenica insertPorudzbenica(Porudzbenica porudzbenica) {
        //TODO: Implement
        return null;
    }

}
