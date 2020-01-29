package njt.supplier.SupplierApp.service;

import njt.supplier.SupplierApp.entity.Dobavljac;

import javax.transaction.Transactional;
import java.util.List;

public interface DobavljacService {
    @Transactional
    List<Dobavljac> getAllDobavljaci();

    @Transactional
    Dobavljac getDobavljacPrekoID(int idDobavljaca);

    @Transactional
    Dobavljac insertDobavljac(Dobavljac dobavljac);

    @Transactional
    Dobavljac removeDobavljacByID(int id);

    @Transactional
    Dobavljac patchDobavljac(int id, Dobavljac dobavljac);
}
