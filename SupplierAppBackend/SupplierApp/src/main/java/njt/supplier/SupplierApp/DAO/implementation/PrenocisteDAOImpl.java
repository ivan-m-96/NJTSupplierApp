/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.DAO.implementation;

import java.util.List;
import javax.persistence.EntityManager;

import javax.transaction.Transactional;
import njt.supplier.SupplierApp.DAO.PrenocisteDAO;
import njt.supplier.SupplierApp.entity.Katalog;
import njt.supplier.SupplierApp.entity.Prenociste;
import njt.supplier.SupplierApp.entity.StavkaKataloga;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ivan
 */
@Repository
public class PrenocisteDAOImpl implements PrenocisteDAO {

    private EntityManager entityManager;

    @Autowired
    public PrenocisteDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<Prenociste> getAll() {

        Session session = entityManager.unwrap(Session.class);

        Query<Prenociste> query = session.createQuery("from Prenociste", Prenociste.class);

        List<Prenociste> prenocista = query.getResultList();

        return prenocista;
    }

    
}
