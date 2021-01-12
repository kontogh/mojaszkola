package pl.school.apkaszkolna.guis.nauczycielskie;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Personel;

@PageTitle("Nauczyciel")
@UIScope
public class TeacherView extends VerticalLayout {
    public TeacherView() {
        var tenpersonel = VaadinSession.getCurrent().getAttribute(Personel.class);

        H2 witaj = new H2(" Pamiętaj: ");
        H3 info = new H3(" Nie opuszczaj stanowiska z zalogowanym kontem, aby niepowołane osoby nie wprowadzały zmian.");
        H3 info1 = new H3(" Codziennie na ostaniej lekcji w zakładce Wynagrodznie zapisz liczbę przepracowanych przez ciebie godzin danego dnia.");
        H3 info2 = new H3(" Najbezpieczniejszym sposobem wyjścia z aplikacji jest użycie przycisku Wyloguj na panelu bocznym.");
        H3 info3 = new H3(" W razie problemów  z aplikacją zgłoś się do adminstracji szkoły.");

        add(new H1("Witaj "+tenpersonel.getImie()+"  "+tenpersonel.getNazwisko()), witaj, info,info1,info2, info3);
    }
}
