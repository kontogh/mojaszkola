package pl.school.apkaszkolna.entities;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Personel extends Uzytkownik{

    private String przedmiot;
    private int hPracy;
    private double pensja;
    @OneToMany(mappedBy = "nauczyciel")
    private List<PlanLekcji> planylekcji;

    public Personel(){
    }
    public Personel(String login, String password, Rola rola){
        this.username = login;
        this.rola = rola;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt );
    }

    public Personel(String imie, String nazwisko, String pesel, String miasto, String ulica, String nrDomu,
                    String poczta, String dataUrodzenia, String username, String password, Rola rola,
                    String przedmiot, int hPracy, double pensja) {
        super(imie, nazwisko, pesel, miasto, ulica, nrDomu, poczta, dataUrodzenia, username, password, rola);
        this.przedmiot = przedmiot;
        this.hPracy = hPracy;
        this.pensja = pensja;
    }

    public boolean checkPassword(String password){
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public Personel(String imie, String nazwisko, String przedmiot, Rola rola){
        this.imie = imie;
        this.nazwisko= nazwisko;
        this.przedmiot = przedmiot;
        this.rola = rola;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrzedmiot() {
        return przedmiot;
    }

    public void setPrzedmiot(String przedmiot) {
        this.przedmiot = przedmiot;
    }

    public int gethPracy() {
        return hPracy;
    }

    public void sethPracy(int hPracy) {
        this.hPracy = hPracy;
    }

    public double getPensja() {
        return pensja;
    }

    public void setPensja(double pensja) {
        this.pensja = pensja;
    }

    public List<PlanLekcji> getPlanylekcji() {
        return planylekcji;
    }

    public void setPlanylekcji(List<PlanLekcji> planylekcji) {
        this.planylekcji = planylekcji;
    }
}

