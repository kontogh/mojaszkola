package pl.school.apkaszkolna.guis.rodzic;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Rodzic;
import pl.school.apkaszkolna.repos.RodzicRepo;
import pl.school.apkaszkolna.repos.UczenRepo;

@UIScope
@PageTitle("Dane Rodzica")
public class DaneRodzica extends VerticalLayout {
    private UczenRepo repo;
    private RodzicRepo rrepo;

    public DaneRodzica(UczenRepo repo, RodzicRepo rrepo) {
        this.repo = repo;
        this.rrepo = rrepo;

        this.setJustifyContentMode(JustifyContentMode.CENTER)   ;
        HorizontalLayout hl = new HorizontalLayout(new H1("Uzupełnij dane"));
        hl.setWidthFull(); hl.setJustifyContentMode(JustifyContentMode.CENTER);

        HorizontalLayout hl3 = new HorizontalLayout(new H3("Podaj dane kontaktowe. Możesz je zmienić w każdej chwili"));
        hl3.setWidthFull(); hl3.setJustifyContentMode(JustifyContentMode.CENTER);

        TextField email = new TextField("Podaj adres email");
        email.setWidthFull();
        TextField nretelTF = new TextField("Podaj numer telefonu");
        nretelTF.setWidthFull();
        HorizontalLayout hlkontakt = new HorizontalLayout();
        hlkontakt.setWidthFull();
        hlkontakt.setJustifyContentMode(JustifyContentMode.CENTER);
        hlkontakt.add(email, nretelTF);

        Button submit = new Button("Zatwierdź kontakty");

        HorizontalLayout hl2 = new HorizontalLayout(new H3("Aby mieć dostęp do danych swojego podobiecznego wypełnij ten formularz"));
        hl2.setWidthFull();
        hl2.setJustifyContentMode(JustifyContentMode.CENTER);
        TextField studUsername = new TextField("login ucznia");
        studUsername.setWidthFull();
        PasswordField studPassword = new PasswordField("hasło ucznia");
        studPassword.setWidthFull();
        HorizontalLayout hl4 = new HorizontalLayout();
        hl4.setWidthFull();
        hl4.setJustifyContentMode(JustifyContentMode.CENTER);
        hl4.add(studUsername, studPassword);
        Button submitucznia = new Button("Zatwierdź ucznia");
        submitucznia.setWidth("167px");
        submit.addClickListener(event ->{
           var tenrodzic = VaadinSession.getCurrent().getAttribute(Rodzic.class);
           tenrodzic.setEmail(email.getValue());
           tenrodzic.setNrTelefonu(nretelTF.getValue());
           rrepo.save(tenrodzic);
            Notification.show("Zapisano dane kontaktowe", 2500, Notification.Position.MIDDLE);
        });
        submitucznia.addClickListener(ev->{
            var dzieckorodzica = repo.getByUsername(studUsername.getValue());
            if(dzieckorodzica !=null && dzieckorodzica.checkPassword(studPassword.getValue())){
                var thisparent = VaadinSession.getCurrent().getAttribute(Rodzic.class);
                dzieckorodzica.setRodzic(thisparent);
                repo.save(dzieckorodzica);
                Notification.show("Uczen został przypisany", 2500, Notification.Position.MIDDLE);
           }
            else {
                Notification err = new Notification("Bład. Nie ma ucznia o takich danych");
                err.addThemeVariants(NotificationVariant.LUMO_ERROR);
                err.setPosition(Notification.Position.MIDDLE);
                err.setDuration(2500);
                err.setOpened(true);
            }
        });
        add(hl, hl3, hlkontakt,submit, hl2,hl4, submitucznia );
    }
}
