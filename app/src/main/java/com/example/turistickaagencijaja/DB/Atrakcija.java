package com.example.turistickaagencijaja.DB;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "atrakcija")
public class Atrakcija {


    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "naziv")
    private String naziv;
    @DatabaseField(columnName = "opis")
    private String opis;
    @DatabaseField(columnName = "foto")
    private String foto;
    @DatabaseField(columnName = "adresa")
    private String adresa;
    @DatabaseField(columnName = "brojTelefona")
    private int brojTelefona;
    @DatabaseField(columnName = "web")
    private String webAdresa;
    @DatabaseField(columnName = "radnoVreme")
    private String radnoVreme;
    @DatabaseField(columnName = "cenaUlazince")
    private int cenaUlaznice;
    @DatabaseField(columnName = "komentar")
    private String komentari;

    public Atrakcija() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(int brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getWebAdresa() {
        return webAdresa;
    }

    public void setWebAdresa(String webAdresa) {
        this.webAdresa = webAdresa;
    }

    public String getRadnoVreme() {
        return radnoVreme;
    }

    public void setRadnoVreme(String radnoVreme) {
        this.radnoVreme = radnoVreme;
    }

    public int getCenaUlaznice() {
        return cenaUlaznice;
    }

    public void setCenaUlaznice(int cenaUlaznice) {
        this.cenaUlaznice = cenaUlaznice;
    }

    public String getKomentari() {
        return komentari;
    }

    public void setKomentari(String komentari) {
        this.komentari = komentari;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
