package njt.supplier.SupplierApp.service.implementation;

import njt.supplier.SupplierApp.DAO.DobavljacDAO;
import njt.supplier.SupplierApp.entity.Dobavljac;
import njt.supplier.SupplierApp.service.DobavljacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DobavljacServiceImpl implements DobavljacService {

    private DobavljacDAO dobavljacDAO;

    @Autowired
    public DobavljacServiceImpl(DobavljacDAO dobavljacDAO) {
        this.dobavljacDAO = dobavljacDAO;
    }

    @Override
    @Transactional
    public List<Dobavljac> getAllDobavljaci() {
        return dobavljacDAO.getAllDobavljaci();
    }

    @Override
    @Transactional
    public Dobavljac getDobavljacPrekoID(int idDobavljaca) {
        return dobavljacDAO.getDobavljacPrekoID(idDobavljaca);
    }

    @Override
    @Transactional
    public Dobavljac insertDobavljac(Dobavljac dobavljac) {
        return dobavljacDAO.insertDobavljac(dobavljac);
    }

    @Override
    @Transactional
    public Dobavljac removeDobavljacByID(int id) {
        return dobavljacDAO.removeDobavljacByID(id);
    }

    @Override
    @Transactional
    public Dobavljac patchDobavljac(int id, Dobavljac dobavljac) {
        return dobavljacDAO.patchDobavljac(id, dobavljac);
    }
}
