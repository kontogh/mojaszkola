package pl.school.apkaszkolna.guis.rodzic;

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
import pl.school.apkaszkolna.entities.Obecnosc;
import pl.school.apkaszkolna.entities.Personel;
import pl.school.apkaszkolna.entities.Rodzic;
import pl.school.apkaszkolna.entities.Uczen;
import pl.school.apkaszkolna.repos.ObecnoscRepo;
import pl.school.apkaszkolna.repos.UczenRepo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

;

@UIScope
@PageTitle("Nieobecności uczniów")
public class Absencje extends VerticalLayout {
    private ObecnoscRepo repo;
    private UczenRepo uczrepo;

    public Absencje(ObecnoscRepo repo, UczenRepo uczrepo) {
        this.repo = repo;
        this.uczrepo = uczrepo;

        var tenrodzic = VaadinSession.getCurrent().getAttribute(Rodzic.class);

        Div txt = new Div();
        Div idd = new Div();
        List<Uczen> kids = this.uczrepo.dziecirodzica(tenrodzic.getId());
        Map<String, Long> imieID = kids.stream().collect(Collectors.toMap(Uczen::getImie, Uczen::getId));

        Select<Uczen> select = new Select<>();
        select.setLabel("Wybierz ucznia");
        select.setItemLabelGenerator(Uczen::getImie);
        select.setItems(kids);
        select.addValueChangeListener(ev ->{
            txt.setText(ev.getValue().getImie());
        });

        Button check = new Button("Pokaż oceny");
        check.setWidth("192px");
        HorizontalLayout hl = new HorizontalLayout(select, check);

        Grid<Obecnosc> obecnoscGrid = new Grid<>(Obecnosc.class);
        obecnoscGrid.removeAllColumns();
        obecnoscGrid.addColumn(Obecnosc::getData).setHeader("Data");
        obecnoscGrid.addColumn(Obecnosc::gethOd).setHeader("Od godziny");
        obecnoscGrid.addColumn(Obecnosc::gethDo).setHeader("Do godziny");

        check.addClickListener(event -> {
            if(select.isEmpty()){
                Notification.show("Wybierz ucznia", 2000, Notification.Position.MIDDLE);
            }
            else {
                try{
                    idd.setText(""+imieID.get(txt.getText()));
                    //pobieram z bazy danych nieobecności dla ucznia o podanycm id
                    List<Obecnosc> obecnoscList = repo.getforStudId(Long.parseLong(idd.getText()));

                    obecnoscGrid.setItems(obecnoscList);
                }
                catch (Exception e){
                    Notification.show("Brak nieobecności", 3000, Notification.Position.MIDDLE);
                }
            }
        });

        add(new H1("Nieobecności podopiecznych"),hl, obecnoscGrid);
    }
}
