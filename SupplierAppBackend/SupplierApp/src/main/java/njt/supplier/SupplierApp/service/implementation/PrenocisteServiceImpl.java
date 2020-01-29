package njt.supplier.SupplierApp.service.implementation;

import njt.supplier.SupplierApp.DAO.PrenocisteDAO;
import njt.supplier.SupplierApp.entity.Prenociste;
import njt.supplier.SupplierApp.service.PrenocisteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PrenocisteServiceImpl implements PrenocisteService {
    PrenocisteDAO prenocisteDAO;

    @Autowired
    public PrenocisteServiceImpl(PrenocisteDAO prenocisteDAO) {
        this.prenocisteDAO = prenocisteDAO;
    }

    @Override
    @Transactional
    public List<Prenociste> getAll() {
        return prenocisteDAO.getAll();
    }
}
