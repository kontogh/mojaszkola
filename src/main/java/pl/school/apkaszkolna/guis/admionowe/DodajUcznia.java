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
import pl.school.apkaszkolna.entities.Dziennik;
import pl.school.apkaszkolna.entities.Klasa;
import pl.school.apkaszkolna.entities.Rola;
import pl.school.apkaszkolna.entities.Uczen;
import pl.school.apkaszkolna.guis.common.PeselField;
import pl.school.apkaszkolna.guis.komponenty.Przedmioty;
import pl.school.apkaszkolna.repos.DziennikRepo;
import pl.school.apkaszkolna.repos.KlasaRepo;
import pl.school.apkaszkolna.repos.UczenRepo;

import java.util.Optional;

@UIScope
@PageTitle("Dodaj Ucznia")
public class DodajUcznia extends FormLayout {

    private KlasaRepo klasaRepo;
    private UczenRepo uczenRepo;
    private DziennikRepo repo;

    public DodajUcznia(KlasaRepo klasaRepo, UczenRepo uczenRepo, DziennikRepo repo) {
        this.klasaRepo = klasaRepo;
        this.uczenRepo = uczenRepo;
        this.repo = repo;
        TextField klasaTF = new TextField("Klasa");
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

        TextField loginTF = new TextField("Login");
        loginTF.setPlaceholder("Przepisz i wręcz uczniowi");
        PasswordField hasloTF = new PasswordField("Hasło");
        hasloTF.setPlaceholder("Przepisz i wręcz uczniowi");
        Div dataurodzenia = new Div();
        DatePicker datePicker = new DatePicker();
        datePicker.setLabel("Data urodzenia");
        datePicker.addValueChangeListener(event -> {
           dataurodzenia.setText(""+event.getValue());
        });

        Button dodaj = new Button("Zapisz ucznia");
        dodaj.addClickListener(event ->{

            var uczen = new Uczen(loginTF.getValue(), hasloTF.getValue(), Rola.STUD);
            uczen.setImie(imieTF.getValue()); uczen.setNazwisko(nazwiskoTF.getValue());uczen.setPesel(peselTF.getValue());
            uczen.setMiasto(miastoTF.getValue()); uczen.setUlica(ulicaTF.getValue()); uczen.setNrDomu(nrDomuTF.getValue());
            uczen.setPoczta(pocztaTF.getValue()+pocztamiastoTF.getValue()); uczen.setDataUrodzenia(dataurodzenia.getText());

            Optional<Klasa> temp = klasaRepo.findById(klasaTF.getValue());
            if(temp.isPresent()){
                uczen.setKlasa(temp.get());
            }
            else {
                var klasa = new Klasa(klasaTF.getValue());
                klasaRepo.save(klasa );
                uczen.setKlasa(klasa);
            }
            uczenRepo.save(uczen);
            for (Przedmioty prz : Przedmioty.values()) {
                repo.save(new Dziennik("", prz.name(), uczen));
            }
            /*var uczniadziennik = new Dziennik("", "Polski", uczen);
            var uczniadziennik2 = new Dziennik("", "Fizyka", uczen);*/
            //repo.save(uczniadziennik); repo.save(uczniadziennik2);
            Notification note = new Notification("Zapisano pomyślnie");
            note.setDuration(3000);
            note.setPosition(Notification.Position.MIDDLE);
            note.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            note.setOpened(true);
        });

        add(new H2("Wprowadź dane"),klasaTF, imieTF, nazwiskoTF, peselTF, miastoTF,
                ulicaTF,nrDomuTF,hlpoczty,datePicker,loginTF, hasloTF, dodaj);

    }
}
