package pl.school.apkaszkolna.entities;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
public class Rodzic extends Uzytkownik {

    private String nrTelefonu;
    private String email;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rodzic")
    private List<Uczen> kids;

    public Rodzic() {
    }
    public Rodzic(String login, String password, Rola rola){
        this.username = login;
        this.rola = rola;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt );
    }

    public Rodzic(String imie, String nazwisko, String pesel, String miasto, String ulica,
                  String nrDomu, String poczta, String dataUrodzenia, String username,
                  String password, Rola rola, String nrTelefonu, String email) {
        super(imie, nazwisko, pesel, miasto, ulica, nrDomu, poczta, dataUrodzenia, username, password, rola);
        this.nrTelefonu = nrTelefonu;
        this.email = email;
    }

    public boolean checkPassword(String password){
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public String getNrTelefonu() {
        return nrTelefonu;
    }

    public void setNrTelefonu(String nrTelefonu) {
        this.nrTelefonu = nrTelefonu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
