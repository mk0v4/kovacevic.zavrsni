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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Marko Kovačević
 */
@Entity
@Table
public class AnalizaCijene extends Entitet implements Serializable {

    private String oznakaNorme, grupaNorme, opis, jedinicaMjere;

    private BigDecimal ukupanNormativVremena, ukupnaCijenaMaterijal, ukupnaCijenaRad, koeficijentFirme, sveukupanIznos;

    @OneToMany (mappedBy = "analizaCijene")
    private List<AnalizaRad> analizeRadova;
    
    @OneToMany (mappedBy = "analizaCijene")
    private List<AnalizaMaterijal> analize_materijala;
    
    @ManyToOne
    private StavkaTroskovnik stavkaTroskovnik;

    public StavkaTroskovnik getStavkaTroskovnik() {
        return stavkaTroskovnik;
    }

    public void setStavkaTroskovnik(StavkaTroskovnik stavkaTroskovnik) {
        this.stavkaTroskovnik = stavkaTroskovnik;
    }
    
    public String getOznakaNorme() {
        return oznakaNorme;
    }

    public void setOznakaNorme(String oznakaNorme) {
        this.oznakaNorme = oznakaNorme;
    }

    public String getGrupaNorme() {
        return grupaNorme;
    }

    public void setGrupaNorme(String grupaNorme) {
        this.grupaNorme = grupaNorme;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getJedinicaMjere() {
        return jedinicaMjere;
    }

    public void setJedinicaMjere(String jedinicaMjere) {
        this.jedinicaMjere = jedinicaMjere;
    }

    public BigDecimal getUkupanNormativVremena() {
        return ukupanNormativVremena;
    }

    public void setUkupanNormativVremena(BigDecimal ukupanNormativVremena) {
        this.ukupanNormativVremena = ukupanNormativVremena;
    }

    public BigDecimal getUkupnaCijenaMaterijal() {
        return ukupnaCijenaMaterijal;
    }

    public void setUkupnaCijenaMaterijal(BigDecimal ukupnaCijenaMaterijal) {
        this.ukupnaCijenaMaterijal = ukupnaCijenaMaterijal;
    }

    public BigDecimal getUkupnaCijenaRad() {
        return ukupnaCijenaRad;
    }

    public void setUkupnaCijenaRad(BigDecimal ukupnaCijenaRad) {
        this.ukupnaCijenaRad = ukupnaCijenaRad;
    }

    public BigDecimal getKoeficijentFirme() {
        return koeficijentFirme;
    }

    public void setKoeficijentFirme(BigDecimal koeficijentFirme) {
        this.koeficijentFirme = koeficijentFirme;
    }

    public BigDecimal getSveukupanIznos() {
        return sveukupanIznos;
    }

    public void setSveukupanIznos(BigDecimal sveukupanIznos) {
        this.sveukupanIznos = sveukupanIznos;
    }

    public List<AnalizaRad> getAnalizeRadova() {
        return analizeRadova;
    }

    public void setAnalizeRadova(List<AnalizaRad> analizeRadova) {
        this.analizeRadova = analizeRadova;
    }

    public List<AnalizaMaterijal> getAnalize_materijala() {
        return analize_materijala;
    }

    public void setAnalize_materijala(List<AnalizaMaterijal> analize_materijala) {
        this.analize_materijala = analize_materijala;
    }

        @Override
    public String toString(){
        return getOznakaNorme() + ", " + getGrupaNorme() + ", " + getOpis();
    }
    
}
