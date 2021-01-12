package pl.school.apkaszkolna.guis.admionowe;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.WydarzeniaPozalekcyne;
import pl.school.apkaszkolna.repos.WydarzeniaRepo;

@UIScope
@PageTitle("Dodaj wydarzenie")
public class DodajWydarzenia extends VerticalLayout {
    private WydarzeniaRepo repo;

    public DodajWydarzenia(WydarzeniaRepo repo) {
        this.repo = repo;
        TextField opisTF = new TextField("Podaj opis wydarzenia");
        opisTF.setWidthFull();
        opisTF.setMinHeight("150px");
        TextField urlTF = new TextField("Podaj url do obrazka ");
        urlTF.setPlaceholder("Preferowane 128x128");
        urlTF.setWidthFull();
        DatePicker dp = new DatePicker("Zaznacz datę dodania");
        Div text = new Div();
        dp.addValueChangeListener(ev -> text.setText(ev.getValue().toString()));
        Button zapisz = new Button("Zapisz wydarzenie");
        zapisz.addClickListener(event -> {
            try {
                repo.save(new WydarzeniaPozalekcyne(text.getText(), opisTF.getValue(), urlTF.getValue()));
                Notification.show("Dodano wydarzenie", 2500, Notification.Position.MIDDLE);
                opisTF.clear();urlTF.clear();
            }
            catch (Exception e){
                Notification.show("Nie udało się dodać wydarzenia", 2500, Notification.Position.MIDDLE);
            }
        });

        add(new H1("Tworzenie nowych wydarzeń"), opisTF, urlTF,dp, zapisz);
    }
}