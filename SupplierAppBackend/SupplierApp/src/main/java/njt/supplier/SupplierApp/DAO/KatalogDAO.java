/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.DAO;

import java.util.List;
import njt.supplier.SupplierApp.entity.Katalog;

/**
 *
 * @author Ivan
 */
public interface KatalogDAO {
    public List<Katalog> getAllKatalozi();

    public List<Katalog> getAllKataloziZaDobavljaca(int idDobavljaca);

    public Katalog getKatalogById(int id);
}
