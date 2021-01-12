package pl.school.apkaszkolna.entities;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
public class Uczen extends Uzytkownik {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idKlasy", nullable=false)
    private Klasa klasa;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "uczen")
    private List <Obecnosc> obecnosc;
    @OneToMany(mappedBy = "stud", fetch = FetchType.LAZY)
    private List<Dziennik> dziennik;
    @ManyToOne(fetch = FetchType.LAZY)
    private Rodzic rodzic;

    public Uczen() {
    }

    public Uczen(String imie, String nazwisko, String pesel, String miasto,
                 String ulica, String nrDomu, String poczta, String dataUrodzenia,
                 String username, String password, Rola rola) {
        super(imie, nazwisko, pesel, miasto, ulica, nrDomu, poczta, dataUrodzenia, username, password, rola);
    }

    public Uczen(String login, String password, Rola rola){
        this.username = login;
        this.rola = rola;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt );
    }
    public boolean checkPassword(String password){
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);

    }

    public Klasa getKlasa() {
        return klasa;
    }

    public String getKlasId(){
        return klasa.getIdKlasy();
    }

    public void setKlasa(Klasa klasa) {
        this.klasa = klasa;
    }

    public List<Obecnosc> getObenosc() {
        return obecnosc;
    }

    public void setObenosc(List<Obecnosc> obecnosc) {
        this.obecnosc = obecnosc;
    }

    public List<Obecnosc> getObecnosc() {
        return obecnosc;
    }

    public void setObecnosc(List<Obecnosc> obecnosc) {
        this.obecnosc = obecnosc;
    }

    public List<Dziennik> getDziennik() {
        return dziennik;
    }

    public void setDziennik(List<Dziennik> dziennik) {
        this.dziennik = dziennik;
    }

    public Rodzic getRodzic() {
        return rodzic;
    }

    public void setRodzic(Rodzic rodzic) {
        this.rodzic = rodzic;
    }

    public long getParentId(){
        return rodzic.getId();
    }
}
