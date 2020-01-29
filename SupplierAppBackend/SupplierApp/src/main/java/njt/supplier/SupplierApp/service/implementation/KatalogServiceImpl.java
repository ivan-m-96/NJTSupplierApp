package njt.supplier.SupplierApp.service.implementation;

import njt.supplier.SupplierApp.DAO.KatalogDAO;
import njt.supplier.SupplierApp.entity.Katalog;
import njt.supplier.SupplierApp.service.KatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class KatalogServiceImpl implements KatalogService {

    KatalogDAO katalogDAO;

    @Autowired
    public KatalogServiceImpl(KatalogDAO katalogDAO) {
        this.katalogDAO = katalogDAO;
    }

    @Override
    @Transactional
    public List<Katalog> getAllKatalozi() {
        return katalogDAO.getAllKatalozi();
    }

    @Override
    @Transactional
    public Katalog getKatalogById(int id) {
        return katalogDAO.getKatalogById(id);
    }

    @Override
    @Transactional
    public List<Katalog> getAllKataloziZaDobavljaca(int idDobavljaca) {
        return katalogDAO.getAllKataloziZaDobavljaca(idDobavljaca);
    }
}
