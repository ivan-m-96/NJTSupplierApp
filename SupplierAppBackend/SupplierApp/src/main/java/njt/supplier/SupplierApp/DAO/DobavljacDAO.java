/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.DAO;

import java.util.List;
import njt.supplier.SupplierApp.entity.Dobavljac;

/**
 *
 * @author Ivan
 */
public interface DobavljacDAO {
    public List<Dobavljac> getAllDobavljaci();

    public Dobavljac getDobavljacPrekoID(int idDobavljaca);
}
