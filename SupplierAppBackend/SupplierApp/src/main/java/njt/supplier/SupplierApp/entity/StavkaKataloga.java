/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ivan
 */
@Entity
@Table(name = "stavka_kataloga")
public class StavkaKataloga {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name= "naziv")
    private String naziv;
    @ManyToOne
    @JoinColumn(name = "proizvodId")
    private Proizvod proizvod;
    @ManyToOne
    @JoinColumn(name = "katalogId")
    private Katalog katalog;
    
    public StavkaKataloga() {
    }

    public StavkaKataloga(int id, String naziv, Proizvod proizvod, Katalog katalog) {
        this.id = id;
        this.naziv = naziv;
        this.proizvod = proizvod;
        this.katalog = katalog;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }

    public Katalog getKatalog() {
        return katalog;
    }

    public void setKatalog(Katalog katalog) {
        this.katalog = katalog;
    }

    
    
    @Override
    public String toString() {
        return "StavkaKataloga{" + "id=" + id + ", naziv=" + naziv + ", proizvod=" + proizvod + '}';
    }
    
    
    
}
