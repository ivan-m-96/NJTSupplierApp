/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.DAO;

import java.util.List;
import njt.supplier.SupplierApp.entity.Porudzbenica;

/**
 *
 * @author Ivan
 */
public interface PorudzbenicaDAO {
    public Porudzbenica getPorudzbenicaPrekoID(int id);

    public List<Porudzbenica> getAllPorudzbeniceZaDobavljaca(int idDobavljaca);

    public Porudzbenica insertPorudzbenica(Porudzbenica porudzbenica);
}
