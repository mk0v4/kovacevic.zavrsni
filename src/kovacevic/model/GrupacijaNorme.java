/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.model;

import java.io.Serializable;
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
public class GrupacijaNorme extends Entitet implements Serializable {
    
    private String oznakaNorme, grupaNorme, opis;
    
    @OneToMany(mappedBy = "grupacijaNorme")
    private List<AnalizaCijene> analizeCijena;

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

    public List<AnalizaCijene> getAnalizeCijena() {
        return analizeCijena;
    }

    public void setAnalizeCijena(List<AnalizaCijene> analizeCijena) {
        this.analizeCijena = analizeCijena;
    }
    
}
