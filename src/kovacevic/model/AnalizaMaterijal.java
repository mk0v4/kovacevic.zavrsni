/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
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

    @Column(precision = 19, scale = 4)
    private BigDecimal kolicina;

    @Column(precision = 19, scale = 4)
    private BigDecimal jedinicnaCijenaMaterijal;

    @Column(precision = 19, scale = 4)
    private BigDecimal cijenaMaterijal;

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
        return /*analiza_cijene.getOznaka_norme() + ", " + */ materijal.getGrupaMaterijal() + ", " + materijal.getOznaka() /*+ ", " + getKolicina() + ", Iznos: " + getCijenaMaterijal() + " kn"*/;
    }

}
