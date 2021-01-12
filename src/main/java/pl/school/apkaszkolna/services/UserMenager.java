package pl.school.apkaszkolna.services;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.school.apkaszkolna.entities.*;
import pl.school.apkaszkolna.guis.komponenty.Przedmioty;
import pl.school.apkaszkolna.repos.*;

@Service
public class UserMenager {

    private PersonelRepo nrepo;
    private UczenRepo uczenRepo;
    private RodzicRepo rodzicRepo;
    private KlasaRepo klasaRepo;
    private WydarzeniaRepo eventy;
    private DziennikRepo dziennik;
    private SalaRepo salaRepo;
    public UserMenager(PersonelRepo nrepo, UczenRepo uczenRepo, RodzicRepo rodzicRepo,
                       KlasaRepo klasaRepo, WydarzeniaRepo eventy, DziennikRepo dziennik,
                       SalaRepo salaRepo) {
        this.nrepo = nrepo;
        this.uczenRepo = uczenRepo;
        this.rodzicRepo = rodzicRepo;
        this.klasaRepo = klasaRepo;
        this.eventy = eventy;
        this.dziennik = dziennik;
        this.salaRepo = salaRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fill(){
        var uucz = new Uczen("ucz", "ucz", Rola.STUD  );
        uucz.setImie("Waldemar"); uucz.setNazwisko("Puchacz"); uucz.setDataUrodzenia("2011-01-07");
        uucz.setPoczta("02-122 Warszawa");uucz.setMiasto("Warszawa"); uucz.setUlica("Szaserów");
        uucz.setPesel("11010778912"); uucz.setNrDomu("15/4A");
        var uucz2 = new Uczen("Krystyna", "Wiśniowska", "11111134567", "Warszawa", "Chłopickiego", "13/2A",
                "02-12 Warszawa", "2011-11-11", "uucz2", "u2", Rola.STUD);

        var klas = new Klasa("3b");
        klasaRepo.save(klas);
        uucz.setKlasa(klas);
        uczenRepo.save(uucz);
        uucz2.setKlasa(klas); uczenRepo.save(uucz2);

        var dzienniczek = new Dziennik("3, 4, 3, 4, 3", "Fizyka", uucz);
        var dziennik1= new Dziennik("3, 3, 3, 4, 3", "Fizyka", uucz2);
        var dziennik2= new Dziennik("3, 5, 5, 4, 3", "Polski", uucz);
        var dziennik3= new Dziennik("3, 6, 4, 4, 3", "Polski", uucz2);
        dziennik.save(dzienniczek); dziennik.save(dziennik1); dziennik.save(dziennik2); dziennik.save(dziennik3);

        var teach = new Personel("teacher", "t", Rola.TEACHER);
        teach.sethPracy(25);teach.setPrzedmiot("Niemiecki"); teach.setPensja(21.00);
        teach.setImie("Maria");teach.setNazwisko("Dębowska");teach.setDataUrodzenia("1977-02-09");
        teach.setMiasto("Warszawa");teach.setPoczta("02-125 Warszawa");teach.setUlica("Chłopickiego");
        teach.setNrDomu("40");
        nrepo.save(teach);
        var adm = new Personel("admin", "a", Rola.ADMIN); adm.setImie("admin");
        adm.setNazwisko("admin"); adm.setPrzedmiot("Dyrektor");
        nrepo.save(adm);

        eventy.save(new WydarzeniaPozalekcyne(" 20.05.2020", "30 kwietnia naszą szkołę odwiedzi prezydent Rzeczypospolitej. W związku z tym zajęcia tego dnia się nie odbędą. Obowiązuje stój galowy",
                "https://a.wattpad.com/useravatar/_duperele_.128.923447.jpg"));
        eventy.save( new WydarzeniaPozalekcyne("23.05.2020", "Pani Dębowska przypomina o następnym spotkaniu grupy muzycznej które odbędzie się 4 Kwietnia o godzinie 15:00, w sali numer 15 ",
                "https://image.flaticon.com/icons/png/128/2061/2061056.png"));
        eventy.save(new WydarzeniaPozalekcyne("25.05.2020", "Dyrekcja szkoły przypomina zwłaszcza rodzicom o wywiadówce, która odbędzie się 12 kwietnia od godziny 16:00.",
                "https://www.vsb.bc.ca/District/PublishingImages/meeting.png"));
        /***
         * Tworzę pod dwie sale na jeden przedmiot
         */
        for (Przedmioty p :
                Przedmioty.values()) {
           // salaRepo.save(new Sala(p));
            salaRepo.save(new Sala(p));
        }

    }
}
