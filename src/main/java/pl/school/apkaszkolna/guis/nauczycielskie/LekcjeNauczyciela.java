package pl.school.apkaszkolna.guis.nauczycielskie;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Personel;
import pl.school.apkaszkolna.entities.PlanLekcji;
import pl.school.apkaszkolna.guis.komponenty.Dni;
import pl.school.apkaszkolna.services.FillPersonel;

@UIScope
@PageTitle("Grafik")
public class LekcjeNauczyciela extends VerticalLayout {

    private FillPersonel fp;

    public LekcjeNauczyciela(FillPersonel fp) {
        this.fp = fp;

        var thispersonel = VaadinSession.getCurrent().getAttribute(Personel.class);

        Grid<PlanLekcji> grid = new Grid<>();
        grid.removeAllColumns();
        grid.addColumn(PlanLekcji::getKlsaId).setHeader("Klasa");
        grid.addColumn(PlanLekcji::getSalaPLid).setHeader("Numer Sali");
        grid.addColumn(PlanLekcji::gethOd).setHeader("Godzina");

        Div txt = new Div();
        Select dayofweek = new Select<>();
        dayofweek.setLabel("Dzień tygodnia");
        dayofweek.setItems(Dni.values());
        dayofweek.addValueChangeListener(ev-> txt.setText(ev.getValue().toString()));

        Button znajdz = new Button("Pokaż");
        znajdz.addClickListener(event -> {
           if(dayofweek.isEmpty()){
               Notification.show("Wybierz dzień tygodnia", 2500, Notification.Position.MIDDLE);
           }
           else {
                grid.setItems(fp.planByTecher(thispersonel.getId(), txt.getText()));
           }
        });

        HorizontalLayout hl = new HorizontalLayout(dayofweek,znajdz );
        hl.setWidthFull();

        add(new H1("Moje zajęcia"), hl, grid);

    }
}
