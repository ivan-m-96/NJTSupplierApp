/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Ivan
 */
@Entity
@Table(name = "stavka_porudzbenice")
@JsonIgnoreProperties(value="porudzbenica")
public class StavkaPorudzbenice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name= "kolicina")
    private int kolicina;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "porudzbenicaId")
    private Porudzbenica porudzbenica;
    @ManyToOne
    @JoinColumn (name = "stavkaKatalogaId")
    private StavkaKataloga stavkaKataloga;
    @Transient
    private boolean zaBrisanje;
    
    public StavkaPorudzbenice() {
    }

    public StavkaPorudzbenice( int kolicina, Porudzbenica porudzbenica, StavkaKataloga stavkaKataloga, boolean zaBrisanje) {
        
        this.kolicina = kolicina;
        this.porudzbenica = porudzbenica;
        this.stavkaKataloga = stavkaKataloga;
        this.zaBrisanje = zaBrisanje;
    }

    public boolean isZaBrisanje() {
        return zaBrisanje;
    }

    public void setZaBrisanje(boolean zaBrisanje) {
        this.zaBrisanje = zaBrisanje;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Porudzbenica getPorudzbenica() {
        return porudzbenica;
    }

    public void setPorudzbenica(Porudzbenica porudzbenica) {
        this.porudzbenica = porudzbenica;
    }

    public StavkaKataloga getStavkaKataloga() {
        return stavkaKataloga;
    }

    public void setStavkaKataloga(StavkaKataloga stavkaKataloga) {
        this.stavkaKataloga = stavkaKataloga;
    }

    @Override
    public String toString() {
        return "StavkaPorudzbenice{" + "id=" + id + ", kolicina=" + kolicina + ", porudzbenica="  + ", stavkaKataloga=" + stavkaKataloga + ", zaBrisanje=" + zaBrisanje + '}';
    }

  
    
    
}
