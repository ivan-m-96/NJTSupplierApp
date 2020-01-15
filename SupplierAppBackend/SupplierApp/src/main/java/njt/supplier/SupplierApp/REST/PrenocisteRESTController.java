/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.REST;

import java.util.List;
import njt.supplier.SupplierApp.DAO.DobavljacDAO;
import njt.supplier.SupplierApp.DAO.KatalogDAO;
import njt.supplier.SupplierApp.DAO.PorudzbenicaDAO;
import njt.supplier.SupplierApp.DAO.PrenocisteDAO;
import njt.supplier.SupplierApp.entity.Dobavljac;
import njt.supplier.SupplierApp.entity.Katalog;
import njt.supplier.SupplierApp.entity.Porudzbenica;
import njt.supplier.SupplierApp.entity.Prenociste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ivan
 */
@RestController
@RequestMapping("/api")
public class PrenocisteRESTController {

    private PrenocisteDAO prenocisteDAO;
    private KatalogDAO katalogDAO;
    private DobavljacDAO dobavljacDAO;
    private PorudzbenicaDAO porudzbenicaDAO;

    public PrenocisteRESTController(PrenocisteDAO prenocisteDAO, KatalogDAO katalogDAO, DobavljacDAO dobavljacDAO, PorudzbenicaDAO porudzbenicaDAO) {
        this.prenocisteDAO = prenocisteDAO;
        this.katalogDAO = katalogDAO;
        this.dobavljacDAO = dobavljacDAO;
        this.porudzbenicaDAO = porudzbenicaDAO;
    }
    
    

    @GetMapping("/prenociste")
    public List<Prenociste> getAllPrenociste() {
        return prenocisteDAO.getAll();
    }

    @GetMapping("/katalozi")
    public List<Katalog> getAllKatalozi() {
        return katalogDAO.getAllKatalozi();
    }

    @GetMapping("/katalozi/{id}")
    public Katalog getKatalogById(@PathVariable(name = "id") int id) {
        return katalogDAO.getKatalogById(id);
    }

    @GetMapping("/dobavljaci/{idDobavljaca}/katalozi")
    public List<Katalog> getAllKataloziZaDobavljaca(@PathVariable(name = "idDobavljaca") int idDobavljaca) {
        return katalogDAO.getAllKataloziZaDobavljaca(idDobavljaca);
    }

    @GetMapping("/dobavljaci")
    public List<Dobavljac> getAllDobavljaci() {
        return dobavljacDAO.getAllDobavljaci();
    }

    @GetMapping("/dobavljaci/{idDobavljaca}")
    public Dobavljac getDobavljacPrekoID(@PathVariable(name = "idDobavljaca") int idDobavljaca) {
        return dobavljacDAO.getDobavljacPrekoID(idDobavljaca);
    }

    @GetMapping("/dobavljaci/{idDobavljaca}/porudzbenice")
    public List<Porudzbenica> getAllPorudzbeniceZaDobavljaca(@PathVariable(name = "idDobavljaca") int idDobavljaca) {
        return porudzbenicaDAO.getAllPorudzbeniceZaDobavljaca(idDobavljaca);
    }

    @GetMapping("/porudzbenice/{idPorudzbenice}")
    public Porudzbenica getPorudzbenicaPrekoID(@PathVariable(name = "idPorudzbenice") int id) {
        return porudzbenicaDAO.getPorudzbenicaPrekoID(id);
    }

    @PostMapping("/porudzbenice")
    public Porudzbenica insertPorudzbenica(@RequestBody Porudzbenica porudzbenica) {
        System.out.println("PORUDZBENICA:");
        System.out.println(porudzbenica);
        return porudzbenicaDAO.insertPorudzbenica(porudzbenica);
//        return porudzbenica;
    }

    @DeleteMapping("/porudzbenice/{id}")
    public Porudzbenica deletePorudzbenica(@PathVariable(name = "id") int id){
        System.out.println("Porudzbenica to delete: " + id) ;
        return porudzbenicaDAO.deletePorudzbenicaById(id);
    }
    
    @PatchMapping("/porudzbenice/{id}")
    public Porudzbenica patchPorudzbenica(@PathVariable(name = "id") int id, @RequestBody Porudzbenica porudzbenica){
        return porudzbenicaDAO.patchPorudzbenica(id, porudzbenica);
                
    }
    
    @PostMapping("/dobavljaci")
    public Dobavljac insertDobavljac(@RequestBody Dobavljac dobavljac) {
        return dobavljacDAO.insertDobavljac(dobavljac);
    }

    @DeleteMapping("/dobavljaci/{id}")
    public Dobavljac deleteDobavljac(@PathVariable(name = "id") int id) {
        return dobavljacDAO.removeDobavljacByID(id);
    }

    @PatchMapping("/dobavljaci/{id}")
    public Dobavljac patchDobavljac(@PathVariable(name = "id") int id, @RequestBody Dobavljac dobavljac) {
        return dobavljacDAO.patchDobavljac(id, dobavljac);
    }

}
