package pl.school.apkaszkolna.guis.rodzic;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Dziennik;
import pl.school.apkaszkolna.entities.Rodzic;
import pl.school.apkaszkolna.entities.Uczen;
import pl.school.apkaszkolna.repos.DziennikRepo;
import pl.school.apkaszkolna.repos.UczenRepo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@PreserveOnRefresh
@UIScope
@PageTitle("Oceny dzieci")
public class KidsMarks extends VerticalLayout {
    private DziennikRepo repo;
    private UczenRepo uczrepo;

    public KidsMarks(DziennikRepo repo, UczenRepo uczrepo) {
        this.repo = repo;
        this.uczrepo = uczrepo;

        Rodzic tenrodzic= VaadinSession.getCurrent().getAttribute(Rodzic.class);
        List<Uczen> kids = uczrepo.dziecirodzica(tenrodzic.getId());
        Map<String, Long>  imieID = kids.stream().collect(Collectors.toMap(Uczen::getImie, Uczen::getId));

        Select<Uczen> select = new Select<>();
        select.setLabel("Wybierz ucznia");
        select.setItemLabelGenerator(Uczen::getImie);
        select.setItems(kids);

        Div tekst = new Div();
        Div idd = new Div();

        /***  Zapisuje imię wybranego z listy dziecka */
        select.addValueChangeListener(ev ->{
         tekst.setText(ev.getValue().getImie());
        });
        Button check = new Button("Pokaż oceny");
        check.setWidth("192px");
        HorizontalLayout hl = new HorizontalLayout(select, check);

        Grid<Dziennik> grid = new Grid<>(Dziennik.class);
        grid.removeAllColumns();
        grid.addColumn(Dziennik::getPrzedmiot).setHeader("Przedmiot");
        grid.addColumn(Dziennik::getOceny).setHeader("Oceny");

        /**
         * Ponieważ rodzice nadają swoim dzieciom różne imiona nie zdaży się sytuacja
         * że w liście rodziców dziecka wystąpi więcej niż jedno z takim imieniem
         */
        check.addClickListener(event ->{
            //pobieram id dziecka dla podanego imienia z mapy
            if(tekst.getText().isEmpty()){
                Notification.show("Nie wybrano ucznia", 2500, Notification.Position.MIDDLE);
            }
            else {
                idd.setText(""+imieID.get(tekst.getText()));
                List<Dziennik> dziennik = repo.ocenyucznia(Long.parseLong(idd.getText()));

                grid.setItems(dziennik);
            }

        } );
            add(new H1("Oceny ucznia"),hl, grid);

    }
}
