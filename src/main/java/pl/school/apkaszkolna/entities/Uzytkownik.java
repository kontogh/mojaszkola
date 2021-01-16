package pl.school.apkaszkolna.entities;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.Index;

import javax.persistence.*;

@MappedSuperclass
public class Uzytkownik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected String imie;
    protected String nazwisko;
    protected String pesel;
    protected String miasto;
    protected String ulica;
    protected String nrDomu;
    protected String poczta;
    protected String dataUrodzenia;
    protected String username;
    protected String passwordHash;
    protected String passwordSalt;

    @Enumerated(EnumType.STRING)
    protected  Rola rola;

    public Uzytkownik(String login, String password, Rola rola){
        this.username = login;
        this.rola = rola;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt );
    }
    public boolean checkPassword(String password){
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public Uzytkownik(String imie, String nazwisko, String pesel, String miasto, String ulica,
                      String nrDomu, String poczta, String dataUrodzenia, String username,
                      String password, Rola rola) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.miasto = miasto;
        this.ulica = ulica;
        this.nrDomu = nrDomu;
        this.poczta = poczta;
        this.dataUrodzenia = dataUrodzenia;
        this.username = username;
        this.passwordSalt =  RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt );
        this.rola = rola;
    }

    public Uzytkownik() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getNrDomu() {
        return nrDomu;
    }

    public void setNrDomu(String nrDomu) {
        this.nrDomu = nrDomu;
    }

    public String getPoczta() {
        return poczta;
    }

    public void setPoczta(String poczta) {
        this.poczta = poczta;
    }

    public String getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(String dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public Rola getRola() {
        return rola;
    }

    public void setRola(Rola rola) {
        this.rola = rola;
    }


    public Uzytkownik(String imie, String nazwisko, String pesel,
                      String miasto, String ulica, String nrDomu, String poczta,
                      String dataUrodzenia, Rola rola) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.miasto = miasto;
        this.ulica = ulica;
        this.nrDomu = nrDomu;
        this.poczta = poczta;
        this.dataUrodzenia = dataUrodzenia;
        this.rola = rola;
    }

}


