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
public class AnalizaRad extends Entitet implements Serializable {

    private String opisOperacije;

    private int brojOperacije;

    private BigDecimal jedinicniNormativVremena, cijenaVrijeme;

    public String getOpisOperacije() {
        return opisOperacije;
    }

    public void setOpisOperacije(String opisOperacije) {
        this.opisOperacije = opisOperacije;
    }

    public int getBrojOperacije() {
        return brojOperacije;
    }

    public void setBrojOperacije(int brojOperacije) {
        this.brojOperacije = brojOperacije;
    }

    public BigDecimal getJedinicniNormativVremena() {
        return jedinicniNormativVremena;
    }

    public void setJedinicniNormativVremena(BigDecimal jedinicniNormativVremena) {
        this.jedinicniNormativVremena = jedinicniNormativVremena;
    }

    public BigDecimal getCijenaVrijeme() {
        return cijenaVrijeme;
    }

    public void setCijenaVrijeme(BigDecimal cijenaVrijeme) {
        this.cijenaVrijeme = cijenaVrijeme;
    }

    @ManyToOne
    private Rad rad;

    @ManyToOne
    private AnalizaCijene analizaCijene;

    public Rad getRad() {
        return rad;
    }

    public void setRad(Rad rad) {
        this.rad = rad;
    }

    public AnalizaCijene getAnalizaCijene() {
        return analizaCijene;
    }

    public void setAnalizaCijene(AnalizaCijene analizaCijene) {
        this.analizaCijene = analizaCijene;
    }

//    @Override
//    public String toString() {
//        return /*analiza_cijene.getOznaka_norme() + ", " +*/ brojOperacije + ", " + opisOperacije + ", " + rad.getGrupaRadova() + " " + rad.getKategorijaRad() + ", " + getJedinicniNormativVremena() + " h, " + getCijenaVrijeme() + " kn/h";
//    }
}
