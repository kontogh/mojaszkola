package pl.school.apkaszkolna.guis.nauczycielskie;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Personel;
import pl.school.apkaszkolna.repos.PersonelRepo;
//todo sprawdzic czy ta adnotacja pomaga
@UIScope
@PageTitle("Wynagrodzenie")
public class Pensja extends VerticalLayout {
    private PersonelRepo repo;

    public Pensja(PersonelRepo repo) {
        this.repo = repo;
        var user = VaadinSession.getCurrent().getAttribute(Personel.class);
        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidthFull();
        hl.setSpacing(true);
        hl.add(new H3("Godziny przepracowane w tym miesiącu: " + user.gethPracy()),
                new H3("Zarobiłeś w tym miesiącu: "+ (user.gethPracy()*user.getPensja())));
        hl.setAlignItems(Alignment.CENTER);
        Button zapisz = new Button("Zapisz dniówkę");
        Select dniowka = new Select();
        Div tekst = new Div();
        dniowka.setLabel("Liczba przepracowaych dziś godzin");
        dniowka.setWidth("175px");
        dniowka.setItems("1", "2", "3", "4", "5","6","7","8","9","10");
        dniowka.addValueChangeListener(ev-> tekst.setText( ""+ev.getValue()));
        int h = user.gethPracy();
        zapisz.addClickListener(event -> {
            user.sethPracy(h+Integer.valueOf(tekst.getText()));
            repo.save(user);
            dniowka.setEnabled(false);
            zapisz.setEnabled(false);
        });
        HorizontalLayout hl2 = new HorizontalLayout();
        hl2.setWidthFull();
        hl2.add(dniowka, zapisz);

        add(new H1("Wynagrodzenie"),hl, hl2);
    }
}
