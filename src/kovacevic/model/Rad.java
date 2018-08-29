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
public class Rad extends Entitet implements Serializable {

    private String grupaRadova, kategorijaRad;

    private BigDecimal cijena;

    public String getGrupaRadova() {
        return grupaRadova;
    }

    public void setGrupaRadova(String grupaRadova) {
        this.grupaRadova = grupaRadova;
    }

    public String getKategorijaRad() {
        return kategorijaRad;
    }

    public void setKategorijaRad(String kategorijaRad) {
        this.kategorijaRad = kategorijaRad;
    }

    public BigDecimal getCijena() {
        return cijena;
    }

    public void setCijena(BigDecimal cijena) {
        this.cijena = cijena;
    }

    @OneToMany(mappedBy = "rad")
    private List<AnalizaRad> analizeRadova;

    public List<AnalizaRad> getAnalizeRadova() {
        return analizeRadova;
    }

    public void setAnalizeRadova(List<AnalizaRad> analizeRadova) {
        this.analizeRadova = analizeRadova;
    }

    @Override
    public String toString() {
        return "Grupa: " + grupaRadova + " Kategorija: " + kategorijaRad + " Cijena: " + cijena + " kn/h";
    }

}
