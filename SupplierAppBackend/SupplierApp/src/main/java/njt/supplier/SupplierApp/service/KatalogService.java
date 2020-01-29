package njt.supplier.SupplierApp.service;

import njt.supplier.SupplierApp.entity.Katalog;

import java.util.List;

public interface KatalogService {
    List<Katalog> getAllKatalozi();

    Katalog getKatalogById(int id);

    List<Katalog> getAllKataloziZaDobavljaca(int idDobavljaca);
}
