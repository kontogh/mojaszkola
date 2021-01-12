package pl.school.apkaszkolna.guis.uczniowe;

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
import pl.school.apkaszkolna.entities.PlanLekcji;
import pl.school.apkaszkolna.entities.Uczen;
import pl.school.apkaszkolna.guis.komponenty.Dni;
import pl.school.apkaszkolna.services.FillPersonel;

import java.util.List;

@UIScope
@PageTitle("Plan Lekcji")
public class LekcjiPlan extends VerticalLayout {
    private FillPersonel fp;

    public LekcjiPlan(FillPersonel fp) {
        this.fp = fp;

        Uczen stud = VaadinSession.getCurrent().getAttribute(Uczen.class);

        Grid<PlanLekcji> grid = new Grid<>();
        grid.removeAllColumns();
        grid.addColumn(PlanLekcji::getPrzedmiot).setHeader("Przedmiot");
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
                try{
                    List<PlanLekcji>  planList =  fp.planByKlasa(stud.getKlasId(), txt.getText());
                    grid.setItems(planList);
                }
                catch (Exception exep ){
                    Notification.show("Błąd. Nie dodano planu lekcji dla twojej klasy");
                }

            }
        });

        HorizontalLayout hl = new HorizontalLayout(dayofweek,znajdz );

        hl.setWidthFull();

        add(new H1("Plan lekcji"), hl, grid);
    }
}
