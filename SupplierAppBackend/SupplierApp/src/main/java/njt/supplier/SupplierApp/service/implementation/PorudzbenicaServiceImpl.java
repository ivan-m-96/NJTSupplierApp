package njt.supplier.SupplierApp.service.implementation;

import njt.supplier.SupplierApp.DAO.PorudzbenicaDAO;
import njt.supplier.SupplierApp.entity.Porudzbenica;
import njt.supplier.SupplierApp.service.PorudzbenicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PorudzbenicaServiceImpl implements PorudzbenicaService {

    PorudzbenicaDAO porudzbenicaDAO;

    @Autowired
    public PorudzbenicaServiceImpl(PorudzbenicaDAO porudzbenicaDAO) {
        this.porudzbenicaDAO = porudzbenicaDAO;
    }

    @Override
    @Transactional
    public List<Porudzbenica> getAllPorudzbeniceZaDobavljaca(int idDobavljaca) {
        return porudzbenicaDAO.getAllPorudzbeniceZaDobavljaca(idDobavljaca);
    }

    @Override
    @Transactional
    public Porudzbenica getPorudzbenicaPrekoID(int id) {
        return porudzbenicaDAO.getPorudzbenicaPrekoID(id);
    }

    @Override
    @Transactional
    public Porudzbenica insertPorudzbenica(Porudzbenica porudzbenica) {
        return porudzbenicaDAO.insertPorudzbenica(porudzbenica);
    }
    @Override
    @Transactional
    public Porudzbenica deletePorudzbenicaById(int id) {
        return porudzbenicaDAO.deletePorudzbenicaById(id);
    }
    @Override
    @Transactional
    public Porudzbenica patchPorudzbenica(int id, Porudzbenica porudzbenica) {
        return porudzbenicaDAO.patchPorudzbenica(id,porudzbenica);
    }
}
