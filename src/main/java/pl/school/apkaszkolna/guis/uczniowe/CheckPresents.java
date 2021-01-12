package pl.school.apkaszkolna.guis.uczniowe;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Obecnosc;
import pl.school.apkaszkolna.entities.Uczen;
import pl.school.apkaszkolna.repos.ObecnoscRepo;

import java.util.Collections;
import java.util.List;

@UIScope
@PageTitle("Nieobecności")
public class CheckPresents extends VerticalLayout {
    private ObecnoscRepo repo;
    private Uczen uczen;

    public CheckPresents(ObecnoscRepo repo) {
        this.repo = repo;
        this.uczen = VaadinSession.getCurrent().getAttribute(Uczen.class);
        List<Obecnosc> obecnoscList = repo.getforStudId(uczen.getId());
        Collections.reverse(obecnoscList);
        Grid<Obecnosc> obecnoscGrid = new Grid<>(Obecnosc.class);
        obecnoscGrid.removeAllColumns();
        obecnoscGrid.addColumn(Obecnosc::getData).setHeader("Data");
        obecnoscGrid.addColumn(Obecnosc::gethOd).setHeader("Od godziny");
        obecnoscGrid.addColumn(Obecnosc::gethDo).setHeader("Do godziny");
        try{

            obecnoscGrid.setItems(obecnoscList);
        }
        catch (Exception e){
            Notification.show("Brak nieobecności", 3000, Notification.Position.MIDDLE);
        }

        add(new H1("Sprawdź nieobecności"), obecnoscGrid);
    }
}
