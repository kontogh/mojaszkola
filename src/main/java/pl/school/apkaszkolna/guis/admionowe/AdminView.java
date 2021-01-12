package pl.school.apkaszkolna.guis.admionowe;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Admin")
public class AdminView extends Div {


    public AdminView() {
        HorizontalLayout hl1 = new HorizontalLayout();


        Button addPlanLek = new Button("Utwórz plan lekcji");
        addPlanLek.setWidth("165px");

        Button events = new Button("Wydarzenia");
        events.setWidth("165px");
        //addUczen.addClickListener(eve -> UI.getCurrent().navigate())
        hl1.add( addPlanLek, addPlanLek, events);
        add(hl1);
    }
}