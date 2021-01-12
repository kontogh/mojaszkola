package pl.school.apkaszkolna.guis.common;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Comments;
import pl.school.apkaszkolna.repos.KomentRepo;
import pl.school.apkaszkolna.services.Dzis;

@UIScope
@PageTitle("Dodaj komentarz")
public class DodajKom extends VerticalLayout {
    private KomentRepo repo;
    private Dzis tfi;

    public DodajKom(KomentRepo repo, Dzis tfi) {
        this.repo = repo;
        this.tfi = tfi;

        TextField trescTF = new TextField("Wpisz treść komentarza");
        trescTF.setWidthFull();
        trescTF.setHeight("150px");
        Button zapisz = new Button("Zapisz komentarz" );
        zapisz.addClickListener(event -> {
            try {
                repo.save(new Comments(trescTF.getValue(), tfi.getDate()));
                Notification.show("Dodano komentarz", 2000, Notification.Position.MIDDLE);
                trescTF.clear();
            }
            catch (Exception e){
                Notification.show("Błąd. Nie udało się zapisać. ", 2500, Notification.Position.MIDDLE);
            }
        });
        add(new H1("Dodawanie kometarzy"),
                new H3("Pamiętaj zaby zachować zasady uczciwości, żetelności i dobrego obyczaju!"),
                trescTF, zapisz);
    }
}
