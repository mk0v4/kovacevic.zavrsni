/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic.pomocno;

import java.util.List;

/**
 *
 * @author Marko Kovačević
 */
public class PorukaIznimke extends Exception {

    private String opis;

    private String greska;

    List<String> greske;

    public PorukaIznimke(String message, String opis, String greska) {
        super(message);
        this.greska = greska;
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getGreska() {
        return greska;
    }

    public void setGreska(String greska) {
        this.greska = greska;
    }

    public List<String> getGreske() {
        return greske;
    }

    public void setGreske(List<String> greske) {
        this.greske = greske;
    }

}
