/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Marko Kovačević
 */
@Entity
@Table
public class StavkaTroskovnik extends Entitet implements Serializable {

    private String dodatanOpis, oznakaStavka;

    public String getOznakaStavka() {
        return oznakaStavka;
    }

    public void setOznakaStavka(String oznakaStavka) {
        this.oznakaStavka = oznakaStavka;
    }

    private BigDecimal kolicinaTroskovnik, ukupnaCijena;

    @OneToMany (mappedBy = "stavkaTroskovnik")
    private List<AnalizaCijene> analizeCijena;

    public List<AnalizaCijene> getAnalizeCijena() {
        return analizeCijena;
    }

    public void setAnalizeCijena(List<AnalizaCijene> analizeCijena) {
        this.analizeCijena = analizeCijena;
    }
    
    public String getDodatanOpis() {
        return dodatanOpis;
    }

    public void setDodatanOpis(String dodatanOpis) {
        this.dodatanOpis = dodatanOpis;
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
