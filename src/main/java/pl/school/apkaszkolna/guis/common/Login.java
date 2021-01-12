package pl.school.apkaszkolna.guis.common;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.services.AuthServices;

import java.awt.*;
@UIScope
@Route(value = "szkola/logowanie")
@PageTitle("Login")
public class Login extends Div {
    private AuthServices authServices;

    public Login(AuthServices authService){
        this.authServices=authService;

        LoginOverlay lo = new LoginOverlay();
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setDescription("Panel logowania do aplikacji");
        Icon ikona = VaadinIcon.ACADEMY_CAP.create();
        ikona.setSize("50px");
        ikona.setColor("white");
        ikona.getStyle().set("top", "-5px");
        H1 title = new H1();
        title.getStyle().set("color", "var(--lumo-base-color)");
        title.add(ikona);
        title.add(new Text("MojaSzkoła"));
        i18n.getForm().setTitle(" Logowanie");
        i18n.getForm().setSubmit("Obwodowa Komisja Wyborcza");
        i18n.getForm().setSubmit("Zaloguj");
        i18n.getForm().setUsername("Nazwa uzytkownika");
        i18n.getForm().setPassword("Hasło");
        i18n.getForm().setForgotPassword("Rejestracja rodzica");
        i18n.getErrorMessage().setTitle("Błąd");
        i18n.getErrorMessage().setMessage("Podano niepoprawne dane");
        i18n.setAdditionalInformation("Jeżeli nie pamietasz hasła, zgłoś się do administracji szkoły");
        lo.setI18n(i18n);
        lo.setTitle(title);
        lo.setOpened(true);
        lo.addForgotPasswordListener(forgotPasswordEvent -> UI.getCurrent().getPage().setLocation("rodzic-rejestracja"));
        lo.addLoginListener(loginEvent -> {try {
            authService.authenticate(loginEvent.getUsername(), loginEvent.getPassword());
            UI.getCurrent().navigate("home");
            UI.getCurrent().getPage().reload();
        }
        catch (AuthServices.AuthExepction exepction){
            lo.setError(true);
        }
        });
        add(lo);
    }

}


