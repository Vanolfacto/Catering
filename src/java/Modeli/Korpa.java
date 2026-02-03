/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeli;

import java.sql.Timestamp;

/**
 *
 * @author Vanja
 */
public class Korpa {
    int id;
    int korisnikID;
    int proizvodID;
    int kolicina;
    double cenaProizvoda;
    double ukupnaCena;
    Timestamp datum;

    public Korpa(int id, int korisnikID, int proizvodID, int kolicina, double cenaProizvoda, double ukupnaCena, Timestamp datum) {
        this.id = id;
        this.korisnikID = korisnikID;
        this.proizvodID = proizvodID;
        this.kolicina = kolicina;
        this.cenaProizvoda = cenaProizvoda;
        this.ukupnaCena = ukupnaCena;
        this.datum = datum;
    }

    public Korpa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(int korisnikID) {
        this.korisnikID = korisnikID;
    }

    public int getProizvodID() {
        return proizvodID;
    }

    public void setProizvodID(int proizvodID) {
        this.proizvodID = proizvodID;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCenaProizvoda() {
        return cenaProizvoda;
    }

    public void setCenaProizvoda(double cenaProizvoda) {
        this.cenaProizvoda = cenaProizvoda;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public void setDatum(Timestamp datum) {
        this.datum = datum;
    }
    
    
}
