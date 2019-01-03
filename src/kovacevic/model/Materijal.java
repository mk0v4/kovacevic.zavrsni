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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Marko Kovačević
 */
@Entity
@Table
public class Materijal extends Entitet implements Serializable {

    private String grupaMaterijal, proizvodac, oznaka, opis, jedinicaMjereAmbalaza;

    @Column(precision = 19, scale = 4)
    private BigDecimal kolicinaAmbalaza;

    @Column(precision = 19, scale = 4)
    private BigDecimal cijenaAmbalaza;

    @OneToMany(mappedBy = "materijal")
    private List<AnalizaMaterijal> analizeMaterijala;

    public String getGrupaMaterijal() {
        return grupaMaterijal;
    }

    public void setGrupaMaterijal(String grupaMaterijal) {
        this.grupaMaterijal = grupaMaterijal;
    }

    public String getProizvodac() {
        return proizvodac;
    }

    public void setProizvodac(String proizvodac) {
        this.proizvodac = proizvodac;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getKolicinaAmbalaza() {
        return kolicinaAmbalaza;
    }

    public void setKolicinaAmbalaza(BigDecimal kolicinaAmbalaza) {
        this.kolicinaAmbalaza = kolicinaAmbalaza;
    }

    public String getJedinicaMjereAmbalaza() {
        return jedinicaMjereAmbalaza;
    }

    public void setJedinicaMjereAmbalaza(String jedinicaMjereAmbalaza) {
        this.jedinicaMjereAmbalaza = jedinicaMjereAmbalaza;
    }

    public BigDecimal getCijenaAmbalaza() {
        return cijenaAmbalaza;
    }

    public void setCijenaAmbalaza(BigDecimal cijenaAmbalaza) {
        this.cijenaAmbalaza = cijenaAmbalaza;
    }

    public List<AnalizaMaterijal> getAnalizeMaterijala() {
        return analizeMaterijala;
    }

    public void setAnalizeMaterijala(List<AnalizaMaterijal> analizeMaterijala) {
        this.analizeMaterijala = analizeMaterijala;
    }

    @Override
    public String toString() {
        String i;
        if (proizvodac.isEmpty()) {
            i = "";
        } else {
            i = ", ";
        }
        return grupaMaterijal + i + proizvodac + ", " + oznaka + ", " + kolicinaAmbalaza + ", " + jedinicaMjereAmbalaza + ", " + cijenaAmbalaza + " kn";
    }

}
