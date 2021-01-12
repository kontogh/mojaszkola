package pl.school.apkaszkolna.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.school.apkaszkolna.entities.*;
import pl.school.apkaszkolna.guis.komponenty.Dni;
import pl.school.apkaszkolna.guis.komponenty.Przedmioty;
import pl.school.apkaszkolna.repos.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FillPersonel {

    @Autowired
   private PersonelRepo repo;
    private SalaRepo salarep;
    private PlanLekcjiRepo plrepo;
    private KlasaRepo klasaRepo;
    private UczenRepo uczenRepo;

    public FillPersonel(PersonelRepo repo, SalaRepo salarep, PlanLekcjiRepo plrepo, KlasaRepo klasaRepo, UczenRepo uczenRepo) {
        this.repo = repo;
        this.salarep = salarep;
        this.plrepo = plrepo;
        this.klasaRepo = klasaRepo;
        this.uczenRepo = uczenRepo;
    }
    public void wypelenij(){
        String tab[] = {"Małgorzata", "Dorota", "Katarzyna", "Robert", "Grażyna", "Wiesław", "Anna"};
        String tab2[] = {"Krajewska", "Nowak", "Bednarska", "Zarówny", "Strugalska", "Konieczny", "Mikołajewska"};
        Przedmioty tab3[] = Przedmioty.values();
        for (int i = 0; i < 7; i++) {
            var tosave =  new Personel(tab[i],tab2[i], tab3[i].name(), Rola.TEACHER);
            repo.save(tosave);
        }
    }
    public List<String> techersubject(){
            List <Personel> teachers = (List<Personel>) repo.findAll();
            List<String> techsubj = new ArrayList<>();
        /**
         * Zwracam lisę nazwiska nauczycieli i przedmiotu jakiego uczy
         */
        for (int i = 0; i < teachers.size() ; i++) {
            techsubj.add(teachers.get(i).getNazwisko() +" - "+teachers.get(i).getPrzedmiot());
        }
        return techsubj;
    }
    public List<String> listaprzedmiow(){

        Przedmioty[] tab = Przedmioty.values();
        List<String> lista = new ArrayList<>();
        for (int i = 0; i <tab.length; i++) {
            lista.add(tab[i].name());
        }
        return lista;
    }

    /**
     *
     * @param p pracownik dla którego zmienione zostaje wynagrodznie
     * @param kasa nowa kwota wynagrodzenia
     */
    public void resetEarnigs(Personel p, double kasa){
        p.setPensja(kasa);
        repo.save(p);
    }
    List<String> getNrSal(){
        List<String> numery = new ArrayList<>();
        List<Sala> sale =(List<Sala>) salarep.findAll();
        for (int i = 0; i < sale.size(); i++) {
            numery.add(sale.get(i).getNrSali()+" - "+sale.get(i).getPrzedmiot());
        }
        return numery;
    }

    /**
     *
     * @param klasa
     * @param przedmiot
     * @param godzina
     * @param
     * @ Zapisywanie planu lekcji dla podanych parametrów
     */
    public void zapiszPL(String klasa, String przedmiot, String godzina, String dni){
        var lekcja = new PlanLekcji(przedmiot, Dni.valueOf(dni), godzina);
        var klas = klasaRepo.findById(klasa);
        var sala = salarep.findbyPrzedmiot(przedmiot);
        var nauczyciel = repo.findByPrzedmiot(przedmiot);
        var klasak = klas.get();

        lekcja.setKlasa(klasak);
        lekcja.setSala(sala);
        lekcja.setNauczyciel(nauczyciel);

        plrepo.save(lekcja);
    }

    public List <PlanLekcji> planByKlasa(String klasid, String dni){
        List<PlanLekcji> planl = plrepo.getByidKlasy(klasid, dni);
        Collections.reverse(planl);
        return planl;
    }
    public List<PlanLekcji> planByTecher(Long teachId, String dni){
        List<PlanLekcji> planLista = plrepo.getByidTeacher(teachId, dni);
        Collections.reverse(planLista);
        return planLista;

    }

    /**
     *
     * @param imie
     * @param nazwisko
     * @param klasa
     * @return zwracam id rodzicza podanego dziecka
     */
    public long findStudPar(String imie,String nazwisko,String klasa){
        var ucze = uczenRepo.getUczen(imie, nazwisko,klasa);
        //było ucze.getId() zwracało id ucznia
        return ucze.getParentId();
    }
}
