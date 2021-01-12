package pl.school.apkaszkolna.guis.nauczycielskie;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import pl.school.apkaszkolna.entities.Obecnosc;
import pl.school.apkaszkolna.repos.ObecnoscRepo;
import pl.school.apkaszkolna.repos.UczenRepo;

@PageTitle("Dodaj nieobecność")
public class DodajNieobecnos extends VerticalLayout {

    private ObecnoscRepo obecnoscRepo;
    private UczenRepo uczenRepo;
//todo najpier dodac zanzacz klase potem hod hdo
    public DodajNieobecnos(ObecnoscRepo obecnoscRepo, UczenRepo uczenRepo) {
        this.obecnoscRepo = obecnoscRepo;
        this.uczenRepo = uczenRepo;
        HorizontalLayout hl1 = new HorizontalLayout();
        hl1.setWidthFull();
        hl1.add(new H1("Dodawanie nieobecności uczniowi"));
        TextField imieTF = new TextField("Podaj imię ucznia ");
        TextField naziwskoTF = new TextField("Podaj nazwisko ucznia ");

        Div txtklasa = new Div();
        Select<String> nrklasySelect = new Select();
        nrklasySelect.setLabel("Wybierz klasę");
        nrklasySelect.setItems("1a", "1b", "2a", "2b", "3a", "3b");
        nrklasySelect.addValueChangeListener(ee-> txtklasa.setText(ee.getValue()));
        nrklasySelect.setWidthFull();

        TextField hod = new TextField("Godzina od której nieobency");
        hod.setPlaceholder("00:00");
        TextField hdo = new TextField("Godzina do której nieobency");
        hdo.setPlaceholder("00:00");
        DatePicker datePicker = new DatePicker("Zaznacz dzień nieobcności");
        FormLayout fl =new FormLayout();
        fl.add(imieTF, naziwskoTF, hod, hdo,nrklasySelect , datePicker);
        Div text = new Div();
        datePicker.addValueChangeListener(ev->text.setText(ev.getValue().toString()));
        Button zapisz = new Button("Zapisz nieobecność");
        zapisz.addClickListener(e ->{
            try {
                var uczen = uczenRepo.getUczen(imieTF.getValue(), naziwskoTF.getValue(),txtklasa.getText());
                if(uczen != null){
                    var nieobecny = new Obecnosc(text.getText(), hod.getValue(), hdo.getValue(), uczen);
                    obecnoscRepo.save(nieobecny);
                    Notification nt = new Notification("Zapisano nieobecność");
                    nt.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    nt.setPosition(Notification.Position.MIDDLE);
                    nt.setDuration(2500);
                    nt.setOpened(true);
                }
                else{
                    Notification note = new Notification("Nie znaleziono ucznia o podanych danych");
                    note.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    note.setPosition(Notification.Position.MIDDLE);
                    note.setDuration(2500);
                    note.setOpened(true);
                }

            }
            catch (Exception exe){
                exe.printStackTrace();
            }

        });
        add(hl1, fl, zapisz);
    }
}
