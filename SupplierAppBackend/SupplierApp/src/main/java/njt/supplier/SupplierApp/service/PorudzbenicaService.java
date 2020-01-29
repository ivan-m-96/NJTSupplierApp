package njt.supplier.SupplierApp.service;

import njt.supplier.SupplierApp.entity.Porudzbenica;

import javax.transaction.Transactional;
import java.util.List;

public interface PorudzbenicaService {
    @Transactional
    List<Porudzbenica> getAllPorudzbeniceZaDobavljaca(int idDobavljaca);

    @Transactional
    Porudzbenica getPorudzbenicaPrekoID(int id);

    @Transactional
    Porudzbenica insertPorudzbenica(Porudzbenica porudzbenica);

    @Transactional
    Porudzbenica deletePorudzbenicaById(int id);

    @Transactional
    Porudzbenica patchPorudzbenica(int id, Porudzbenica porudzbenica);
}
