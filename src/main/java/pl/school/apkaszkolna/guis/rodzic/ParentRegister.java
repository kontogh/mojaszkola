package pl.school.apkaszkolna.guis.rodzic;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.services.AuthServices;

import static java.lang.Thread.sleep;

@UIScope
@Route("rodzic-rejestracja")
public class ParentRegister extends Composite {

    private final AuthServices auth;

    public ParentRegister(AuthServices auth) {
        this.auth = auth;
    }

    @Override
    protected Component initContent() {

        TextField username = new TextField("Nazwa używtkownika");
        PasswordField password = new PasswordField("Hasło");
        PasswordField confirm = new PasswordField("Hasło");
        Button rejestruj = new Button("Zarejestruj");
        rejestruj.addClickListener(event -> rejestracja(username.getValue(), password.getValue(), confirm.getValue()));
        return new VerticalLayout(new H2("Rejestracja"), username, password, confirm, rejestruj);
    }

    private void rejestracja(String val, String val2, String val3 ){
        if(val.trim().isEmpty()){
            Notification.show("Wpisz nazwę użytkownika", 2000, Notification.Position.MIDDLE);
        }
        else if (val2.trim().isEmpty()){
            Notification.show("Wpisz hasło", 2000, Notification.Position.MIDDLE);
        }
        else if (!val2.equals(val3)){
            Notification.show("Hasło się nie zgadzają", 2000, Notification.Position.MIDDLE);
        }
        else {
            auth.rejestruj(val, val2);
            Notification done = new Notification("Rejestracja się powiodła");
            done.setDuration(2500);
            done.setPosition(Notification.Position.MIDDLE);
            done.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            done.setOpened(true);
            try {
                sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                UI.getCurrent().getPage().setLocation("szkola/logowanie");
            }
        }
    }
}
