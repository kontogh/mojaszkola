package pl.school.apkaszkolna.guis.nauczycielskie;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Uczen;
import pl.school.apkaszkolna.guis.komponenty.OcenyUcznia;
import pl.school.apkaszkolna.repos.DziennikRepo;
import pl.school.apkaszkolna.repos.UczenRepo;

import java.util.List;

@UIScope
@PageTitle("Dziennik")
public class DziennikOcen extends VerticalLayout {
    private DziennikRepo repodzi;
    private UczenRepo uczniowie;
    private OcenyUcznia ou;

    public DziennikOcen(DziennikRepo repodzi, UczenRepo uczniowie, OcenyUcznia ou) {
        this.repodzi = repodzi;
        this.uczniowie = uczniowie;
        this.ou = ou;

        Select nrklasy = new Select();
        nrklasy.setLabel("Wybierz klasę");
        nrklasy.setWidth("100x");
        nrklasy.setItems("1a", "1b", "2a", "2b", "3a", "3b");
        Select oceny = new Select();
        oceny.setLabel("Ocena");
        oceny.setItems("1", "2", "3", "4", "5", "6");

        Select subj = new Select();
        subj.setLabel("Przedmioty");
        subj.setItems("Matematyka","Biologia", "Chemia","Geografia","Fizyka","Historia", "Angielski",
                "Niemiecki", "WF","Polski","Informatyka","WOK","PP" );

        Div tekst = new Div();
        Div txtsub = new Div();
        subj.addValueChangeListener(evt->{txtsub.setText(""+evt.getValue());Notification.show(txtsub.getText());});

        nrklasy.addValueChangeListener(ev-> tekst.setText(""+ ev.getValue()));
        Button znajdz = new Button("Znajdź klasę");

        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidthFull();
        hl.add(nrklasy, subj, znajdz);

        Grid <OcenyUcznia> dziennik = new Grid<>(OcenyUcznia.class);
        Binder<OcenyUcznia> binder = new Binder<>(OcenyUcznia.class);
        Editor<OcenyUcznia> editor = dziennik.getEditor();
        editor.setBinder(binder);

        dziennik.removeAllColumns();
        dziennik.addColumn(OcenyUcznia::getImie).setHeader("Imię");
        dziennik.addColumn(OcenyUcznia::getNazwisko).setHeader("Nazwisko");
        Grid.Column<OcenyUcznia> ocenyColumn = dziennik.addColumn(OcenyUcznia::getOceny).setHeader("Oceny");

        TextField ocenyTF = new TextField();
        ocenyColumn.setEditorComponent(item -> {
            binder.bind(ocenyTF, "oceny");
            return ocenyTF; });

        ocenyTF.getElement()
                .addEventListener("keydown",
                        event -> dziennik.getEditor().cancel())
                .setFilter("event.key === 'Tab' && !event.shiftKey");

        dziennik.addItemDoubleClickListener(event -> {
            dziennik.getEditor().editItem(event.getItem());
            ocenyTF.focus();
        });
        binder.addValueChangeListener(event -> {
            dziennik.getEditor().refresh();
        });
        dziennik.getEditor().addCloseListener(event -> {
            if (binder.getBean() != null) {
                ou.zapiszOceny(binder.getBean().getImie(),binder.getBean().getNazwisko(),
                        tekst.getText(), binder.getBean().getOceny(), txtsub.getText());
        }});

        znajdz.addClickListener(klik-> {
            List<OcenyUcznia> uczens = ou.konwerter(tekst.getText(), txtsub.getText());
            dziennik.setItems(uczens);
        });


        add(new H1("Dziennik ocen"), hl, dziennik);
    }
}
