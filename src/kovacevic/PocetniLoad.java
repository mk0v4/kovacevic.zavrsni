/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kovacevic;

import java.math.BigDecimal;
import kovacevic.controller.HibernateObrada;
import kovacevic.model.AnalizaCijene;
import kovacevic.model.AnalizaMaterijal;
import kovacevic.model.AnalizaRad;
import kovacevic.model.GrupacijaNorme;
import kovacevic.model.Materijal;
import kovacevic.model.Rad;
import kovacevic.model.StavkaTroskovnik;

/**
 *
 * @author Marko Kovačević
 */
public class PocetniLoad {

    public static void main(String[] args) {

        HibernateObrada<Rad> obradaRadovi = new HibernateObrada<>();
        Rad rad = new Rad();
        rad.setGrupaRadova("Zidar");
        rad.setKategorijaRad("I");
        rad.setCijena(new BigDecimal(55.00));
        obradaRadovi.save(rad);

        Rad rad1 = new Rad();
        rad1.setGrupaRadova("Zidar");
        rad1.setKategorijaRad("II");
        rad1.setCijena(new BigDecimal(60.00));
        obradaRadovi.save(rad1);

        Rad rad2 = new Rad();
        rad2.setGrupaRadova("Zidar");
        rad2.setKategorijaRad("III");
        rad2.setCijena(new BigDecimal(65.00));
        obradaRadovi.save(rad2);

        Rad rad3 = new Rad();
        rad3.setGrupaRadova("Zidar");
        rad3.setKategorijaRad("IV");
        rad3.setCijena(new BigDecimal(70.00));
        obradaRadovi.save(rad3);

        Rad rad4 = new Rad();
        rad4.setGrupaRadova("Zidar");
        rad4.setKategorijaRad("V");
        rad4.setCijena(new BigDecimal(75.00));
        obradaRadovi.save(rad4);

        Rad rad5 = new Rad();
        rad5.setGrupaRadova("Zidar");
        rad5.setKategorijaRad("VI");
        rad5.setCijena(new BigDecimal(80.00));
        obradaRadovi.save(rad5);

        Rad rad6 = new Rad();
        rad6.setGrupaRadova("Zidar");
        rad6.setKategorijaRad("VII");
        rad6.setCijena(new BigDecimal(85.00));
        obradaRadovi.save(rad6);

        Rad rad7 = new Rad();
        rad7.setGrupaRadova("Zidar");
        rad7.setKategorijaRad("VIII");
        rad7.setCijena(new BigDecimal(90.00));
        obradaRadovi.save(rad7);

        Rad rad8 = new Rad();
        rad8.setGrupaRadova("Radnik");
        rad8.setKategorijaRad("I");
        rad8.setCijena(new BigDecimal(50.00));
        obradaRadovi.save(rad8);

        Rad rad9 = new Rad();
        rad9.setGrupaRadova("Radnik");
        rad9.setKategorijaRad("II");
        rad9.setCijena(new BigDecimal(52.50));
        obradaRadovi.save(rad9);

        Rad rad10 = new Rad();
        rad10.setGrupaRadova("Radnik");
        rad10.setKategorijaRad("III");
        rad10.setCijena(new BigDecimal(55.00));
        obradaRadovi.save(rad10);

        Rad rad11 = new Rad();
        rad11.setGrupaRadova("Radnik");
        rad11.setKategorijaRad("IV");
        rad11.setCijena(new BigDecimal(57.50));
        obradaRadovi.save(rad11);

        Rad rad12 = new Rad();
        rad12.setGrupaRadova("Radnik");
        rad12.setKategorijaRad("V");
        rad12.setCijena(new BigDecimal(60.00));
        obradaRadovi.save(rad12);

        Rad rad13 = new Rad();
        rad13.setGrupaRadova("Radnik");
        rad13.setKategorijaRad("VI");
        rad13.setCijena(new BigDecimal(62.50));
        obradaRadovi.save(rad13);

        Rad rad14 = new Rad();
        rad14.setGrupaRadova("Radnik");
        rad14.setKategorijaRad("VII");
        rad14.setCijena(new BigDecimal(65.00));
        obradaRadovi.save(rad14);

        Rad rad15 = new Rad();
        rad15.setGrupaRadova("Radnik");
        rad15.setKategorijaRad("VIII");
        rad15.setCijena(new BigDecimal(70.00));
        obradaRadovi.save(rad15);

        HibernateObrada<Materijal> obradaMaterijali = new HibernateObrada<>();
        Materijal materijal = new Materijal();
        materijal.setGrupaMaterijal("Cement");
        materijal.setProizvodac("NEXE");
        materijal.setOznaka("CEM II/B-M (P-S) 32,5R");
        materijal.setKolicinaAmbalaza(new BigDecimal(25.00));
        materijal.setJedinicaMjereAmbalaza("kg");
        materijal.setCijenaAmbalaza(new BigDecimal(20.00));
        materijal.setOpis("Standardni mješani portland cement, visoka rana čvrstoća; Razred čvrstoće 32,5 MPa");
        obradaMaterijali.save(materijal);

        Materijal materijal1 = new Materijal();
        materijal1.setGrupaMaterijal("Vapno");
        materijal1.setProizvodac("InterCAL");
        materijal1.setOznaka("DL 80-30-S1");
        materijal1.setKolicinaAmbalaza(new BigDecimal(25.00));
        materijal1.setJedinicaMjereAmbalaza("kg");
        materijal1.setCijenaAmbalaza(new BigDecimal(18.00));
        materijal1.setOpis("Hidratizirano vapno");
        obradaMaterijali.save(materijal1);

        Materijal materijal2 = new Materijal();
        materijal2.setGrupaMaterijal("Voda");
        materijal2.setProizvodac("Gradski vodovod");
        materijal2.setOznaka("Pitka voda");
        materijal2.setKolicinaAmbalaza(new BigDecimal(1.00));
        materijal2.setJedinicaMjereAmbalaza("m3");
        materijal2.setCijenaAmbalaza(new BigDecimal(15.00));
        materijal2.setOpis("Smatra se prikladnom za pripremu i ne treba se ispitivati");
        obradaMaterijali.save(materijal2);

        Materijal materijal3 = new Materijal();
        materijal3.setGrupaMaterijal("Agregat");
        materijal3.setProizvodac("");
        materijal3.setOznaka("Granulacija 0-4 mm");
        materijal3.setKolicinaAmbalaza(new BigDecimal(1.00));
        materijal3.setJedinicaMjereAmbalaza("m3");
        materijal3.setCijenaAmbalaza(new BigDecimal(90.00));
        materijal3.setOpis("Riječni pijesak");
        obradaMaterijali.save(materijal3);

        Materijal materijal4 = new Materijal();
        materijal4.setGrupaMaterijal("Opeka");
        materijal4.setProizvodac("");
        materijal4.setOznaka("15x30x6,5cm");
        materijal4.setKolicinaAmbalaza(new BigDecimal(1.00));
        materijal4.setJedinicaMjereAmbalaza("kom");
        materijal4.setCijenaAmbalaza(new BigDecimal(0.28));
        materijal4.setOpis("");
        obradaMaterijali.save(materijal4);

        Materijal materijal5 = new Materijal();
        materijal5.setGrupaMaterijal("Mort");
        materijal5.setProizvodac("Gradilište");
        materijal5.setOznaka("Produžni mort 1:3:9 - strojno");
        materijal5.setKolicinaAmbalaza(new BigDecimal(1.00));
        materijal5.setJedinicaMjereAmbalaza("m3");
        materijal5.setCijenaAmbalaza(new BigDecimal(304.54));
        materijal5.setOpis("");
        obradaMaterijali.save(materijal5);

        HibernateObrada<StavkaTroskovnik> obradaStavkeTroskovnika = new HibernateObrada<>();
        StavkaTroskovnik stavkaTroskovnik = new StavkaTroskovnik();
        stavkaTroskovnik.setOznakaStavka("1.");
        stavkaTroskovnik.setKolicinaTroskovnik(new BigDecimal(30.52));
        stavkaTroskovnik.setOpisStavka("Početka radova nakon odobrenja nadzornog inženjera.");
        stavkaTroskovnik.setUkupnaCijena(new BigDecimal(21886.50));
        obradaStavkeTroskovnika.save(stavkaTroskovnik);

        HibernateObrada<GrupacijaNorme> obradaGrupacijaNorme = new HibernateObrada<>();
        GrupacijaNorme grupacijaNorme = new GrupacijaNorme();
        grupacijaNorme.setOznakaNorme("GN-301-203");
        grupacijaNorme.setGrupaNorme("Zidraski radovi");
        grupacijaNorme.setOpis("Zidanje prizemlja i katova opekom");
        obradaGrupacijaNorme.save(grupacijaNorme);

        GrupacijaNorme grupacijaNorme1 = new GrupacijaNorme();
        grupacijaNorme1.setOznakaNorme("GN-301-103");
        grupacijaNorme1.setGrupaNorme("Zidraski radovi");
        grupacijaNorme1.setOpis("Spravljanje produžnog morta");
        obradaGrupacijaNorme.save(grupacijaNorme1);

        HibernateObrada<AnalizaCijene> obradaAnalizeCijena = new HibernateObrada<>();
        AnalizaCijene analizaCijene = new AnalizaCijene();
        analizaCijene.setOznakaNorme("GN-301-203-3.1.");
        analizaCijene.setOpis("Zidanje zida punom opekom 15x30x6,5cm u produžnom mortu 1:3:9");
        analizaCijene.setJedinicaMjere("m3");
        analizaCijene.setUkupanNormativVremena(new BigDecimal(8.01));
        analizaCijene.setUkupnaCijenaMaterijal(new BigDecimal(174.45));
        analizaCijene.setUkupnaCijenaRad(new BigDecimal(174.45));
        analizaCijene.setKoeficijentFirme(new BigDecimal(1.05));
        analizaCijene.setSveukupanIznos(new BigDecimal(717.12));
        analizaCijene.setStavkaTroskovnik(stavkaTroskovnik);
        analizaCijene.setGrupacijaNorme(grupacijaNorme);
        obradaAnalizeCijena.save(analizaCijene);

        AnalizaCijene analizaCijene1 = new AnalizaCijene();
        analizaCijene1.setOznakaNorme("GN-301-103-5.3.");
        analizaCijene1.setOpis("Strojna izrada produžnog morta 1:3:9");
        analizaCijene1.setJedinicaMjere("m3");
        analizaCijene1.setUkupanNormativVremena(new BigDecimal(2.03));
        analizaCijene1.setUkupnaCijenaMaterijal(new BigDecimal(304.54));
        analizaCijene1.setUkupnaCijenaRad(new BigDecimal(106.58));
        analizaCijene1.setKoeficijentFirme(new BigDecimal(1.00));
        analizaCijene1.setSveukupanIznos(new BigDecimal(411.12));
        analizaCijene1.setStavkaTroskovnik(stavkaTroskovnik);
        analizaCijene1.setGrupacijaNorme(grupacijaNorme1);
        obradaAnalizeCijena.save(analizaCijene1);

        HibernateObrada<AnalizaMaterijal> obradaAnalizeMaterijala = new HibernateObrada<>();
        AnalizaMaterijal analizaMaterijal = new AnalizaMaterijal();
        analizaMaterijal.setKolicina(new BigDecimal(146.00));
//        analizaMaterijal.setJedinicaMjere("kg");
        analizaMaterijal.setJedinicnaCijenaMaterijal(new BigDecimal(0.80));
        analizaMaterijal.setCijenaMaterijal(new BigDecimal(116.80));
        analizaMaterijal.setAnalizaCijene(analizaCijene1);
        analizaMaterijal.setMaterijal(materijal);
        obradaAnalizeMaterijala.save(analizaMaterijal);

        AnalizaMaterijal analizaMaterijal1 = new AnalizaMaterijal();
        analizaMaterijal1.setKolicina(new BigDecimal(0.31));
//        analizaMaterijal1.setJedinicaMjere("m3");
        analizaMaterijal1.setJedinicnaCijenaMaterijal(new BigDecimal(324.00));
        analizaMaterijal1.setCijenaMaterijal(new BigDecimal(100.44));
        analizaMaterijal1.setAnalizaCijene(analizaCijene1);
        analizaMaterijal1.setMaterijal(materijal1);
        obradaAnalizeMaterijala.save(analizaMaterijal1);

        AnalizaMaterijal analizaMaterijal2 = new AnalizaMaterijal();
        analizaMaterijal2.setKolicina(new BigDecimal(0.93));
//        analizaMaterijal2.setJedinicaMjere("m3");
        analizaMaterijal2.setJedinicnaCijenaMaterijal(new BigDecimal(90.00));
        analizaMaterijal2.setCijenaMaterijal(new BigDecimal(83.70));
        analizaMaterijal2.setAnalizaCijene(analizaCijene1);
        analizaMaterijal2.setMaterijal(materijal2);
        obradaAnalizeMaterijala.save(analizaMaterijal2);

        AnalizaMaterijal analizaMaterijal3 = new AnalizaMaterijal();
        analizaMaterijal3.setKolicina(new BigDecimal(0.24));
//        analizaMaterijal3.setJedinicaMjere("m3");
        analizaMaterijal3.setJedinicnaCijenaMaterijal(new BigDecimal(15.00));
        analizaMaterijal3.setCijenaMaterijal(new BigDecimal(3.60));
        analizaMaterijal3.setAnalizaCijene(analizaCijene1);
        analizaMaterijal3.setMaterijal(materijal3);
        obradaAnalizeMaterijala.save(analizaMaterijal3);

        AnalizaMaterijal analizaMaterijal4 = new AnalizaMaterijal();
        analizaMaterijal4.setKolicina(new BigDecimal(275));
//        analizaMaterijal4.setJedinicaMjere("kom");
        analizaMaterijal4.setJedinicnaCijenaMaterijal(new BigDecimal(0.28));
        analizaMaterijal4.setCijenaMaterijal(new BigDecimal(77.00));
        analizaMaterijal4.setAnalizaCijene(analizaCijene);
        analizaMaterijal4.setMaterijal(materijal4);
        obradaAnalizeMaterijala.save(analizaMaterijal4);

        AnalizaMaterijal analizaMaterijal5 = new AnalizaMaterijal();
        analizaMaterijal5.setKolicina(new BigDecimal(0.32));
//        analizaMaterijal5.setJedinicaMjere("m3");
        analizaMaterijal5.setJedinicnaCijenaMaterijal(new BigDecimal(304.54));
        analizaMaterijal5.setCijenaMaterijal(new BigDecimal(97.45));
        analizaMaterijal5.setAnalizaCijene(analizaCijene);
        analizaMaterijal5.setMaterijal(materijal5);
        obradaAnalizeMaterijala.save(analizaMaterijal5);

        HibernateObrada<AnalizaRad> obradaAnalizeRadova = new HibernateObrada<>();
        AnalizaRad analizaRad = new AnalizaRad();
        analizaRad.setOpisOperacije("Strojno spravljanje morta");
        analizaRad.setBrojOperacije(2);
        analizaRad.setJedinicniNormativVremena(new BigDecimal(1.88));
        analizaRad.setCijenaVrijeme(new BigDecimal(98.70));
        analizaRad.setAnalizaCijene(analizaCijene1);
        analizaRad.setRad(rad9);
        obradaAnalizeRadova.save(analizaRad);

        AnalizaRad analizaRad1 = new AnalizaRad();
        analizaRad1.setOpisOperacije("Prijenos cementa");
        analizaRad1.setBrojOperacije(3);
        analizaRad1.setJedinicniNormativVremena(new BigDecimal(0.15));
        analizaRad1.setCijenaVrijeme(new BigDecimal(7.88));
        analizaRad1.setAnalizaCijene(analizaCijene1);
        analizaRad1.setRad(rad9);
        obradaAnalizeRadova.save(analizaRad1);

        AnalizaRad analizaRad2 = new AnalizaRad();
        analizaRad2.setOpisOperacije("Strojno spravljanje morta");
        analizaRad2.setBrojOperacije(1);
        analizaRad2.setJedinicniNormativVremena(new BigDecimal(0.60));
        analizaRad2.setCijenaVrijeme(new BigDecimal(31.50));
        analizaRad2.setAnalizaCijene(analizaCijene);
        analizaRad2.setRad(rad9);
        obradaAnalizeRadova.save(analizaRad2);

        AnalizaRad analizaRad3 = new AnalizaRad();
        analizaRad3.setOpisOperacije("Zidanje");
        analizaRad3.setBrojOperacije(2);
        analizaRad3.setJedinicniNormativVremena(new BigDecimal(3.96));
        analizaRad3.setCijenaVrijeme(new BigDecimal(316.80));
        analizaRad3.setAnalizaCijene(analizaCijene);
        analizaRad3.setRad(rad5);
        obradaAnalizeRadova.save(analizaRad3);

        AnalizaRad analizaRad4 = new AnalizaRad();
        analizaRad4.setOpisOperacije("Zidanje");
        analizaRad4.setBrojOperacije(2);
        analizaRad4.setJedinicniNormativVremena(new BigDecimal(1.26));
        analizaRad4.setCijenaVrijeme(new BigDecimal(66.15));
        analizaRad4.setAnalizaCijene(analizaCijene);
        analizaRad4.setRad(rad9);
        obradaAnalizeRadova.save(analizaRad4);

        AnalizaRad analizaRad5 = new AnalizaRad();
        analizaRad5.setOpisOperacije("Prijenos opeke");
        analizaRad5.setBrojOperacije(3);
        analizaRad5.setJedinicniNormativVremena(new BigDecimal(1.30));
        analizaRad5.setCijenaVrijeme(new BigDecimal(68.25));
        analizaRad5.setAnalizaCijene(analizaCijene);
        analizaRad5.setRad(rad9);
        obradaAnalizeRadova.save(analizaRad5);

        AnalizaRad analizaRad6 = new AnalizaRad();
        analizaRad6.setOpisOperacije("Prijenos morta");
        analizaRad6.setBrojOperacije(4);
        analizaRad6.setJedinicniNormativVremena(new BigDecimal(0.65));
        analizaRad6.setCijenaVrijeme(new BigDecimal(34.13));
        analizaRad6.setAnalizaCijene(analizaCijene);
        analizaRad6.setRad(rad9);
        obradaAnalizeRadova.save(analizaRad6);

        System.out.println("Završen početni load");
    }

}
