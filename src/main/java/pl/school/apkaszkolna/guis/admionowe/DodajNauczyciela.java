package pl.school.apkaszkolna.guis.admionowe;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Personel;
import pl.school.apkaszkolna.entities.Rola;
import pl.school.apkaszkolna.guis.common.PeselField;
import pl.school.apkaszkolna.repos.PersonelRepo;

@UIScope
@PageTitle("Dodaj Nauczyciela")
public class DodajNauczyciela extends FormLayout {
    private PersonelRepo repo;

    public DodajNauczyciela(PersonelRepo repo) {
        this.repo = repo;
        TextField przedmiot = new TextField("Przedmiot");
        TextField imieTF = new TextField("Imię");
        TextField nazwiskoTF = new TextField("Nazwisko");
        PeselField peselTF = new PeselField();
        TextField miastoTF = new TextField("Miasto");
        TextField ulicaTF = new TextField("Ulica");
        TextField nrDomuTF= new TextField("Numer Domu");

        TextField pocztaTF = new TextField("Poczta");
        pocztaTF.setWidthFull();
        pocztaTF.setPattern("[0-9]{2}-[0-9]{3}");
        pocztaTF.setMaxLength(6);
        pocztaTF.setPlaceholder("np: 33-155");
        TextField pocztamiastoTF = new TextField("Siedziba poczty");
        pocztamiastoTF.setWidthFull();
        HorizontalLayout hlpoczty = new HorizontalLayout(pocztaTF,pocztamiastoTF);

        TextField pensjaTF = new TextField("Stawka za godzinę");
        pensjaTF.setPlaceholder("np: 22.22");
        TextField loginTF = new TextField("Login");
        loginTF.setPlaceholder("Przepisz i wręcz nauczycielowi");
        PasswordField pf = new PasswordField("Hasło");
        pf.setPlaceholder("Zapisz i wręcz nauczycielowi");
        Div dataurodzenia = new Div();
        DatePicker datePicker = new DatePicker();
        pensjaTF.setMaxLength(5);
        datePicker.setLabel("Data urodzenia");
        datePicker.addValueChangeListener(event -> {
            dataurodzenia.setText(""+event.getValue());
        });
        Button dodaj = new Button("Zapisz pracownika");
        dodaj.addClickListener(event ->{
            var staff = new Personel(loginTF.getValue(), pf.getValue(), Rola.TEACHER);
            staff.setImie(imieTF.getValue()); staff.setNazwisko(nazwiskoTF.getValue());
            staff.setPesel(peselTF.getValue());staff.setMiasto(miastoTF.getValue());
            staff.setUlica(ulicaTF.getValue()); staff.setNrDomu(nrDomuTF.getValue());
            staff.setPoczta(pocztaTF.getValue()+pocztamiastoTF.getValue()); staff.setDataUrodzenia(dataurodzenia.getText());
            staff.setPrzedmiot(przedmiot.getValue()); staff.setPensja(Double.valueOf(pensjaTF.getValue()));

            Notification note = new Notification("Zapisano pomyślnie");
            note.setDuration(3000);
            note.setPosition(Notification.Position.MIDDLE);
            note.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            try {
                repo.save(staff);
                note.setOpened(true);
            }
            catch (NumberFormatException exception){
                Notification nt = new Notification("Błąd danych, Sprawdź czy w polu pensja jest kropka");
                nt.setDuration(3000);
                nt.setPosition(Notification.Position.MIDDLE);
                nt.addThemeVariants(NotificationVariant.LUMO_ERROR);
                nt.setOpened(true);
            }
        });
        add(new H2("Wprowadź dane"),przedmiot, imieTF, nazwiskoTF, peselTF, miastoTF,
                ulicaTF,nrDomuTF,hlpoczty,datePicker, loginTF, pf,pensjaTF, dodaj);

    }
}
