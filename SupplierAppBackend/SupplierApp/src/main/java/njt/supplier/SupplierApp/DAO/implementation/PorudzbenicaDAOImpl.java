/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.DAO.implementation;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import njt.supplier.SupplierApp.DAO.PorudzbenicaDAO;
import njt.supplier.SupplierApp.entity.Porudzbenica;
import njt.supplier.SupplierApp.entity.Prenociste;
import njt.supplier.SupplierApp.entity.StavkaPorudzbenice;
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

        try {
            Porudzbenica porudzbenica = query.getSingleResult();
            return porudzbenica;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    @Transactional
    public List<Porudzbenica> getAllPorudzbeniceZaDobavljaca(int idDobavljaca) {
        try {
            Session session = entityManager.unwrap(Session.class);

            Query<Porudzbenica> query = session.createQuery("from Porudzbenica P where P.dobavljac.id=:idDobavljaca", Porudzbenica.class).setParameter("idDobavljaca", idDobavljaca);

            List<Porudzbenica> porudzbenice = query.getResultList();

            return porudzbenice;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public Porudzbenica insertPorudzbenica(Porudzbenica porudzbenica) {

        try {
            Session session = entityManager.unwrap(Session.class);
            for (StavkaPorudzbenice sp : porudzbenica.getStavke()) {
                sp.setPorudzbenica(porudzbenica);

            }
            System.out.println(porudzbenica);
            session.merge(porudzbenica);
            return porudzbenica;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    @Transactional
    public Porudzbenica deletePorudzbenicaById(int id) {
        try {
            Session session = entityManager.unwrap(Session.class);

            Porudzbenica porudzbenica = getPorudzbenicaPrekoID(id);

            session.delete(porudzbenica);
            return porudzbenica;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    @Transactional
    public Porudzbenica patchPorudzbenica(int id, Porudzbenica novaPorudzbenica) {
        try {
            Session session = entityManager.unwrap(Session.class);

            Porudzbenica dbPorudzbenica = session.get(Porudzbenica.class, id);

            dbPorudzbenica.setDatum(novaPorudzbenica.getDatum());

            List<StavkaPorudzbenice> stavkeIzNove = novaPorudzbenica.getStavke();
            List<StavkaPorudzbenice> stavkeZaStaru = new ArrayList<StavkaPorudzbenice>();
            for (StavkaPorudzbenice stavkaPorudzbenice : stavkeIzNove) {
                if (!stavkaPorudzbenice.isZaBrisanje()) {
                    stavkaPorudzbenice.setPorudzbenica(dbPorudzbenica);
                    stavkeZaStaru.add(stavkaPorudzbenice);
                }
            }

            dbPorudzbenica.getStavke().clear();
            dbPorudzbenica.getStavke().addAll(stavkeZaStaru);

            session.merge(dbPorudzbenica);

            return dbPorudzbenica;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
