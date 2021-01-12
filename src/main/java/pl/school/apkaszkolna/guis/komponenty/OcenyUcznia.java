package pl.school.apkaszkolna.guis.komponenty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.school.apkaszkolna.repos.DziennikRepo;
import pl.school.apkaszkolna.repos.UczenRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class OcenyUcznia {

    private String imie;
    private String nazwisko;
    private String oceny;
    @Autowired
    private DziennikRepo repo;
    @Autowired
    private UczenRepo uczrepo;

    public OcenyUcznia(DziennikRepo repo, UczenRepo uczenrepo) {
        this.repo = repo;
        this.uczrepo = uczenrepo;
    }

    public OcenyUcznia() {
    }

    public OcenyUcznia(String imie, String nazwisko, String oceny) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.oceny = oceny;
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

    public String getOceny() {
        return oceny;
    }

    public void setOceny(String oceny) {
        this.oceny = oceny;
    }

    public List<OcenyUcznia> konwerter(String klasa, String przedmiot){

        List<String> imiona = repo.imiona(klasa, przedmiot);
        List<String> nazwiska = repo.nazwiska(klasa, przedmiot);
        List<String> ocenylista = repo.oceny(klasa, przedmiot);
        List<OcenyUcznia> ocenyUcznias =new ArrayList<>();
        for(int i= 0; i <imiona.size(); i++){
            ocenyUcznias.add(new OcenyUcznia(imiona.get(i), nazwiska.get(i), ocenylista.get(i)));
        }
        return ocenyUcznias;
    }

    public void zapiszOceny(String imie, String nazwisko, String klasa, String noweOceny, String przedmiot){

        var tmpuczen = uczrepo.getUczen(imie, nazwisko, klasa);

        var dziennikucz = repo.dziennikucznia(tmpuczen.getId(), przedmiot );

        dziennikucz.setOceny(noweOceny);
        repo.save(dziennikucz);

    }
}
