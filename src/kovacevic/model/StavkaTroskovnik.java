/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Marko Kovačević
 */
@Entity
@Table
public class StavkaTroskovnik extends Entitet implements Serializable {

    private String opisStavka, oznakaStavka;

    @Column(precision = 19, scale = 4)
    private BigDecimal kolicinaTroskovnik;

    @Column(precision = 19, scale = 4)
    private BigDecimal ukupnaCijena;

    @OneToMany(mappedBy = "stavkaTroskovnik")
    private List<AnalizaCijene> analizeCijena;
    
    @ManyToOne
    private Troskovnik troskovnik;

    public Troskovnik getTroksovnik() {
        return troskovnik;
    }

    public void setTroksovnik(Troskovnik troskovnik) {
        this.troskovnik = troskovnik;
    }

    public List<AnalizaCijene> getAnalizeCijena() {
        return analizeCijena;
    }

    public void setAnalizeCijena(List<AnalizaCijene> analizeCijena) {
        this.analizeCijena = analizeCijena;
    }

    public String getOpisStavka() {
        return opisStavka;
    }

    public void setOpisStavka(String opisStavka) {
        this.opisStavka = opisStavka;
    }

    public String getOznakaStavka() {
        return oznakaStavka;
    }

    public void setOznakaStavka(String oznakaStavka) {
        this.oznakaStavka = oznakaStavka;
    }
    
    public BigDecimal getKolicinaTroskovnik() {
        return kolicinaTroskovnik;
    }

    public void setKolicinaTroskovnik(BigDecimal kolicinaTroskovnik) {
        this.kolicinaTroskovnik = kolicinaTroskovnik;
    }

    public BigDecimal getUkupnaCijena() {
        return ukupnaCijena;
    }

    public void setUkupnaCijena(BigDecimal ukupnaCijena) {
        this.ukupnaCijena = ukupnaCijena;
    }

    @Override
    public String toString() {
        return getOznakaStavka();
    }

}
