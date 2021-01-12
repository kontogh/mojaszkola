package pl.school.apkaszkolna.guis.uczniowe;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Dziennik;
import pl.school.apkaszkolna.entities.Uczen;
import pl.school.apkaszkolna.repos.DziennikRepo;

import java.util.List;

@UIScope
@PageTitle("Sprawdź oceny")
public class CheckMarks extends VerticalLayout {
    private DziennikRepo repo;

    public CheckMarks(DziennikRepo repo) {
        this.repo = repo;

        var uczen = VaadinSession.getCurrent().getAttribute(Uczen.class);

        Grid<Dziennik> grid = new Grid<>(Dziennik.class);
        grid.removeAllColumns();
        grid.addColumn(Dziennik::getPrzedmiot).setHeader("Przedmiot");
        grid.addColumn(Dziennik::getOceny).setHeader("Oceny");
        List<Dziennik> dziennik = repo.ocenyucznia(uczen.getId());
        grid.setItems(dziennik);

        add(new H1("Twoje oceny"), grid);
    }
}
