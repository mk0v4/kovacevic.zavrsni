/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Marko Kovačević
 */
@Entity
@Table
public class AnalizaMaterijal extends Entitet implements Serializable {

    private BigDecimal kolicina, jedinicnaCijenaMaterijal, cijenaMaterijal;

    private String jedinicaMjere;
    
    @ManyToOne
    private Materijal materijal;
    
    @ManyToOne
    private AnalizaCijene analizaCijene;

    public BigDecimal getKolicina() {
        return kolicina;
    }

    public void setKolicina(BigDecimal kolicina) {
        this.kolicina = kolicina;
    }

    public BigDecimal getJedinicnaCijenaMaterijal() {
        return jedinicnaCijenaMaterijal;
    }

    public void setJedinicnaCijenaMaterijal(BigDecimal jedinicnaCijenaMaterijal) {
        this.jedinicnaCijenaMaterijal = jedinicnaCijenaMaterijal;
    }

    public BigDecimal getCijenaMaterijal() {
        return cijenaMaterijal;
    }

    public void setCijenaMaterijal(BigDecimal cijenaMaterijal) {
        this.cijenaMaterijal = cijenaMaterijal;
    }

    public String getJedinicaMjere() {
        return jedinicaMjere;
    }

    public void setJedinicaMjere(String jedinicaMjere) {
        this.jedinicaMjere = jedinicaMjere;
    }

    
    public Materijal getMaterijal() {
        return materijal;
    }

    public void setMaterijal(Materijal materijal) {
        this.materijal = materijal;
    }

    public AnalizaCijene getAnalizaCijene() {
        return analizaCijene;
    }

    public void setAnalizaCijene(AnalizaCijene analizaCijene) {
        this.analizaCijene = analizaCijene;
    }
    
    @Override
    public String toString() {
        return /*analiza_cijene.getOznaka_norme() + ", " + */materijal.getGrupaMaterijal() + ", " + materijal.getOznaka() + ", " + getKolicina() + " " + getJedinicaMjere()  + ", Iznos: " + getCijenaMaterijal() +" kn"; 
    }
    
}
