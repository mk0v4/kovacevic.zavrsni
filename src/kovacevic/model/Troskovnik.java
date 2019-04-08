/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Marko Kovačević
 */
@Entity
@Table
public class Troskovnik extends Entitet implements Serializable {
    
    private String oznaka, naslov, objektNaziv, narucitelj, izvodac;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datumIzrade;
    
    @OneToMany(mappedBy = "troskovnik")
    private List<StavkaTroskovnik> stavkeTroskovnika;

    public List<StavkaTroskovnik> getStavkeTroskovnika() {
        return stavkeTroskovnika;
    }

    public void setStavkeTroskovnika(List<StavkaTroskovnik> stavkeTroskovnika) {
        this.stavkeTroskovnika = stavkeTroskovnika;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getObjektNaziv() {
        return objektNaziv;
    }

    public void setObjektNaziv(String objektNaziv) {
        this.objektNaziv = objektNaziv;
    }

    public String getNarucitelj() {
        return narucitelj;
    }

    public void setNarucitelj(String narucitelj) {
        this.narucitelj = narucitelj;
    }

    public String getIzvodac() {
        return izvodac;
    }

    public void setIzvodac(String izvodac) {
        this.izvodac = izvodac;
    }

    public Date getDatumIzrade() {
        return datumIzrade;
    }

    public void setDatumIzrade(Date datumIzrade) {
        this.datumIzrade = datumIzrade;
    }
    
    

}
