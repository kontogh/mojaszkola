package pl.school.apkaszkolna.guis.common;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@PageTitle("Wyloguj")
@Route("szkola/wyloguj")
public class Logout extends Composite<VerticalLayout> {
    public Logout() {
        UI.getCurrent().getPage().setLocation("szkola/logowanie");
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
    }
}
