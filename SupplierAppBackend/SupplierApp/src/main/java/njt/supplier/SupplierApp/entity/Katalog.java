/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njt.supplier.SupplierApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ivan
 */
@Entity
@Table(name = "katalog")
public class Katalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "datum")
    private Date datum;
    @ManyToOne()
    @JoinColumn(name = "dobavljacId")
    private Dobavljac dobavljac;

    public Katalog() {
    }

    public Katalog(int id, Date datum, Dobavljac dobavljac) {
        this.id = id;
        this.datum = datum;
        this.dobavljac = dobavljac;
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

    @Override
    public String toString() {
        return "Katalog{" + "id=" + id + ", datum=" + datum + ", dobavljac=" + dobavljac + '}';
    }

}
