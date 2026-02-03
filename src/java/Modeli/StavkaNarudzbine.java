/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeli;

/**
 *
 * @author Vanja
 */
public class StavkaNarudzbine {
    int id;
    int narudzbinaID;
    int proizvodID;
    String nazivProizvoda;
    int kolicina;
    double cena;

    public StavkaNarudzbine(int id, int narudzbinaID, int proizvodID, String nazivProizvoda, int kolicina, double cena) {
        this.id = id;
        this.narudzbinaID = narudzbinaID;
        this.proizvodID = proizvodID;
        this.nazivProizvoda = nazivProizvoda;
        this.kolicina = kolicina;
        this.cena = cena;
    }

    public StavkaNarudzbine() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNarudzbinaID() {
        return narudzbinaID;
    }

    public void setNarudzbinaID(int narudzbinaID) {
        this.narudzbinaID = narudzbinaID;
    }

    public int getProizvodID() {
        return proizvodID;
    }

    public void setProizvodID(int proizvodID) {
        this.proizvodID = proizvodID;
    }

    public String getNazivProizvoda() {
        return nazivProizvoda;
    }

    public void setNazivProizvoda(String nazivProizvoda) {
        this.nazivProizvoda = nazivProizvoda;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }
    
    
}
