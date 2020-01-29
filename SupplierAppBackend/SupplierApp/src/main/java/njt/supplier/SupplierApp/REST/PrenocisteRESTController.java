/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.REST;

import java.util.List;

import njt.supplier.SupplierApp.DAO.PrenocisteDAO;
import njt.supplier.SupplierApp.entity.Dobavljac;
import njt.supplier.SupplierApp.entity.Katalog;
import njt.supplier.SupplierApp.entity.Porudzbenica;
import njt.supplier.SupplierApp.entity.Prenociste;
import njt.supplier.SupplierApp.service.DobavljacService;
import njt.supplier.SupplierApp.service.KatalogService;
import njt.supplier.SupplierApp.service.PorudzbenicaService;
import njt.supplier.SupplierApp.service.PrenocisteService;
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

    private PrenocisteService prenocisteService;
    private KatalogService katalogService;
    private PorudzbenicaService porudzbenicaService;
    private DobavljacService dobavljacService;

    public PrenocisteRESTController(PrenocisteService prenocisteService, KatalogService katalogService, DobavljacService dobavljacService, PorudzbenicaService porudzbenicaService) {
        this.prenocisteService = prenocisteService;
        this.katalogService = katalogService;
        this.dobavljacService = dobavljacService;
        this.porudzbenicaService = porudzbenicaService;
    }
    
    

    @GetMapping("/prenociste")
    public List<Prenociste> getAllPrenociste() {
        return prenocisteService.getAll();
    }

    @GetMapping("/katalozi")
    public List<Katalog> getAllKatalozi() {
        return katalogService.getAllKatalozi();
    }

    @GetMapping("/katalozi/{id}")
    public Katalog getKatalogById(@PathVariable(name = "id") int id) {
        return katalogService.getKatalogById(id);
    }

    @GetMapping("/dobavljaci/{idDobavljaca}/katalozi")
    public List<Katalog> getAllKataloziZaDobavljaca(@PathVariable(name = "idDobavljaca") int idDobavljaca) {
        return katalogService.getAllKataloziZaDobavljaca(idDobavljaca);
    }

    @GetMapping("/dobavljaci")
    public List<Dobavljac> getAllDobavljaci() {
        return dobavljacService.getAllDobavljaci();
    }

    @GetMapping("/dobavljaci/{idDobavljaca}")
    public Dobavljac getDobavljacPrekoID(@PathVariable(name = "idDobavljaca") int idDobavljaca) {
        return dobavljacService.getDobavljacPrekoID(idDobavljaca);
    }

    @GetMapping("/dobavljaci/{idDobavljaca}/porudzbenice")
    public List<Porudzbenica> getAllPorudzbeniceZaDobavljaca(@PathVariable(name = "idDobavljaca") int idDobavljaca) {
        return porudzbenicaService.getAllPorudzbeniceZaDobavljaca(idDobavljaca);
    }

    @GetMapping("/porudzbenice/{idPorudzbenice}")
    public Porudzbenica getPorudzbenicaPrekoID(@PathVariable(name = "idPorudzbenice") int id) {
        System.out.println("Id porudzbenice " + id);
        return porudzbenicaService.getPorudzbenicaPrekoID(id);
    }

    @PostMapping("/porudzbenice")
    public Porudzbenica insertPorudzbenica(@RequestBody Porudzbenica porudzbenica) {
        System.out.println("PORUDZBENICA:");
        System.out.println(porudzbenica);
        return porudzbenicaService.insertPorudzbenica(porudzbenica);
//        return porudzbenica;
    }

    @DeleteMapping("/porudzbenice/{id}")
    public Porudzbenica deletePorudzbenica(@PathVariable(name = "id") int id){
        System.out.println("Porudzbenica to delete: " + id) ;
        return porudzbenicaService.deletePorudzbenicaById(id);
    }
    
    @PatchMapping("/porudzbenice/{id}")
    public Porudzbenica patchPorudzbenica(@PathVariable(name = "id") int id, @RequestBody Porudzbenica porudzbenica){
        return porudzbenicaService.patchPorudzbenica(id, porudzbenica);
                
    }
    
    @PostMapping("/dobavljaci")
    public Dobavljac insertDobavljac(@RequestBody Dobavljac dobavljac) {
        return dobavljacService.insertDobavljac(dobavljac);
    }

    @DeleteMapping("/dobavljaci/{id}")
    public Dobavljac deleteDobavljac(@PathVariable(name = "id") int id) {
        return dobavljacService.removeDobavljacByID(id);
    }

    @PatchMapping("/dobavljaci/{id}")
    public Dobavljac patchDobavljac(@PathVariable(name = "id") int id, @RequestBody Dobavljac dobavljac) {
        return dobavljacService.patchDobavljac(id, dobavljac);
    }

}
