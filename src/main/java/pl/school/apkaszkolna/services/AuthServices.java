package pl.school.apkaszkolna.services;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;
import pl.school.apkaszkolna.entities.*;
import pl.school.apkaszkolna.guis.admionowe.*;
import pl.school.apkaszkolna.guis.common.DodajKom;
import pl.school.apkaszkolna.guis.common.HomeView;
import pl.school.apkaszkolna.guis.common.Logout;
import pl.school.apkaszkolna.guis.common.MainView;
import pl.school.apkaszkolna.guis.nauczycielskie.*;
import pl.school.apkaszkolna.guis.rodzic.Absencje;
import pl.school.apkaszkolna.guis.rodzic.DaneRodzica;
import pl.school.apkaszkolna.guis.rodzic.KidsMarks;
import pl.school.apkaszkolna.guis.uczniowe.CheckMarks;
import pl.school.apkaszkolna.guis.uczniowe.CheckPresents;
import pl.school.apkaszkolna.guis.uczniowe.LekcjiPlan;
import pl.school.apkaszkolna.repos.PersonelRepo;
import pl.school.apkaszkolna.repos.RodzicRepo;
import pl.school.apkaszkolna.repos.UczenRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServices {
    private PersonelRepo personel;
    private UczenRepo uczenRepo;
    private RodzicRepo rodzicRepo;

    public AuthServices(PersonelRepo personel, UczenRepo uczenRepo, RodzicRepo rodzicRepo) {
        this.personel = personel;
        this.uczenRepo = uczenRepo;
        this.rodzicRepo = rodzicRepo;
    }

    public record AuthorizedRoute(String route, String name, Class<? extends Component> view){

    }
    public static class AuthExepction extends Exception{

        public AuthExepction() {
            super();
        }
    }
    public void authenticate(String username, String password) throws AuthExepction {
        Personel staff = personel.getByUsername(username);
        var uczen = uczenRepo.getByUsername(username);
        var rodzic = rodzicRepo.getByUsername(username);

        if(staff != null && staff.checkPassword(password)){
            VaadinSession.getCurrent().setAttribute(Personel.class, staff);
            createRoutes(staff.getRola());
            }
        else if(uczen != null &&uczen.checkPassword(password)){
            VaadinSession.getCurrent().setAttribute(Uczen.class, uczen);
            createRoutes(uczen.getRola());
        }
        else if(rodzic != null && rodzic.checkPassword(password)){
            VaadinSession.getCurrent().setAttribute(Rodzic.class, rodzic);
            createRoutes(rodzic.getRola());
        }
            else {
                throw new AuthExepction();
            }
        }

    public void createRoutes(Rola rola){//moze wysypac
        getAuthorizedRoutes(rola).stream().forEach(route ->
                RouteConfiguration.forSessionScope().setRoute(route.route, route.view, MainView.class));
    }

    public List<AuthorizedRoute> getAuthorizedRoutes(Rola rola){
        var routes = new ArrayList<AuthorizedRoute>();
        if(rola.equals(Rola.TEACHER)){
            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
            routes.add(new AuthorizedRoute("nauczyciel", "Nauczyciel", TeacherView.class));
            routes.add(new AuthorizedRoute("grafik", "Grafik", LekcjeNauczyciela.class));
            routes.add(new AuthorizedRoute("dziennik", "Dziennik", DziennikOcen.class));
            routes.add(new AuthorizedRoute("nieobecnosc", "Dodaj nieobecność", DodajNieobecnos.class));
            routes.add(new AuthorizedRoute("kasa", "Wynagrodzenie", Pensja.class));
            routes.add(new AuthorizedRoute("nowewydarzenia", "Dodaj wydarzenie", DodajWydarzenia.class));
            routes.add(new AuthorizedRoute("komentarz", "Dodaj komentarz", DodajKom.class));
            routes.add(new AuthorizedRoute("kontakty", "Kontakty rodziców", KontaktdoRodz.class));
            routes.add(new AuthorizedRoute("logout", "Wyloguj", Logout.class));
        }
        else if(rola.equals(Rola.ADMIN)){
            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
            routes.add(new AuthorizedRoute("planlekcji", "Plany Lekcji", StworzPlanLekcji.class));
            routes.add(new AuthorizedRoute("students", "Dodaj Ucznia", DodajUcznia.class));
            routes.add(new AuthorizedRoute("teachers", "Dodaj Nauczyciela", DodajNauczyciela.class));
            routes.add(new AuthorizedRoute("pensje", "Pensje personelu", Wynagrodzenia.class));
            routes.add(new AuthorizedRoute("nowewydarzenia", "Dodaj wydarzenie", DodajWydarzenia.class));
            routes.add(new AuthorizedRoute("kontakty", "Kontakty rodziców", KontaktdoRodz.class));
            routes.add(new AuthorizedRoute("comments", "Komentarze", Komentarze.class));
            routes.add(new AuthorizedRoute("logout", "Wyloguj", Logout.class));
        }
        else if(rola.equals(Rola.STUD)){
            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
            routes.add(new AuthorizedRoute("oceny", "Sprawdź oceny", CheckMarks.class));
            routes.add(new AuthorizedRoute("nieobecnosci", "Nieobecności", CheckPresents.class));
            routes.add(new AuthorizedRoute("moj-planlekcji", "Plan Lekcji", LekcjiPlan.class));
            routes.add(new AuthorizedRoute("komentarz", "Dodaj komentarz", DodajKom.class));
            routes.add(new AuthorizedRoute("logout", "Wyloguj", Logout.class));
        }
        else if(rola.equals(Rola.PARENT)){
            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
            routes.add(new AuthorizedRoute("dane", "Dane Rodzica", DaneRodzica.class));
            routes.add(new AuthorizedRoute("oceny-dzieci", "Oceny dzieci", KidsMarks.class));
            routes.add(new AuthorizedRoute("nieobecnosci-dzieci", "Nieobecności uczniów", Absencje.class));
            routes.add(new AuthorizedRoute("komentarz", "Dodaj komentarz", DodajKom.class));
            routes.add(new AuthorizedRoute("logout", "Wyloguj", Logout.class));
        }
        return routes;
    }

    public void rejestruj(String val, String val2) {
        rodzicRepo.save(new Rodzic(val, val2, Rola.PARENT));
    }

}
