/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Ivan
 */
@Entity
@Table(name = "porudzbenica")
public class Porudzbenica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "datum")
    private Date datum;
    @ManyToOne
    @JoinColumn(name = "dobavljacId")
    private Dobavljac dobavljac;
    @ManyToOne
    @JoinColumn(name = "prenocisteId")
    private Prenociste prenociste;
    @OneToMany(mappedBy = "porudzbenica")
    private List<StavkaPorudzbenice> stavke; 

    public Porudzbenica() {
    }

    public Porudzbenica(int id, Date datum, Dobavljac dobavljac, Prenociste prenociste, List<StavkaPorudzbenice> stavke) {
        this.id = id;
        this.datum = datum;
        this.dobavljac = dobavljac;
        this.prenociste = prenociste;
        this.stavke = stavke;
    }

    public List<StavkaPorudzbenice> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaPorudzbenice> stavke) {
        this.stavke = stavke;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Dobavljac getDobavljac() {
        return dobavljac;
    }

    public void setDobavljac(Dobavljac dobavljac) {
        this.dobavljac = dobavljac;
    }

    public Prenociste getPrenociste() {
        return prenociste;
    }

    public void setPrenociste(Prenociste prenociste) {
        this.prenociste = prenociste;
    }
    
    
    
}
