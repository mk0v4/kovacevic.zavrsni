/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

/**
 *
 * @author Marko Kovačević
 */
@MappedSuperclass
public abstract class Entitet {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private boolean obrisan = false;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datumKreiranja;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datumPromjene;

    public boolean isObrisan() {
        return obrisan;
    }

    public void setObrisan(boolean obrisan) {
        this.obrisan = obrisan;
    }

    public Date getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(Date datum_kreiranja) {
        this.datumKreiranja = datum_kreiranja;
    }

    public Date getDatumPromjene() {
        return datumPromjene;
    }

    public void setDatumPromjene(Date datum_promjene) {
        this.datumPromjene = datum_promjene;
    }

}
