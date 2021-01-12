package pl.school.apkaszkolna.guis.admionowe;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.repos.RodzicRepo;
import pl.school.apkaszkolna.services.FillPersonel;

@UIScope
@PageTitle("Kontakty rodziców")
public class KontaktdoRodz extends VerticalLayout {

    private RodzicRepo repo;
    private FillPersonel fp;

    public KontaktdoRodz(RodzicRepo repo, FillPersonel fp) {
        this.repo = repo;
        this.fp = fp;

        class K{String t;String e; public K(String t, String e){this.t=t;this.e=e;} String getT(){return  t;} String getE(){return e;}
        }
        TextField imieTF = new TextField("Podaj imię");
        imieTF.setWidthFull();
        TextField nazwiskoTF = new TextField("Podaj imię");
        nazwiskoTF.setWidthFull();

        Div txt = new Div();
        Select<String> nrklasySelect = new Select();
        nrklasySelect.setLabel("Wybierz klasę");
        nrklasySelect.setItems("1a", "1b", "2a", "2b", "3a", "3b");
        nrklasySelect.addValueChangeListener(ee-> txt.setText(ee.getValue()));
        nrklasySelect.setWidthFull();

        Grid<K> grid = new Grid<>();
        grid.removeAllColumns();
        grid.addColumn(K::getE).setHeader("email");
        grid.addColumn(K::getT).setHeader("Numer telefonu");
        grid.setHeight("120px");

        HorizontalLayout hl = new HorizontalLayout(imieTF, nazwiskoTF, nrklasySelect);
        Button znajdz = new Button("Znajdź");
        znajdz.addClickListener(e-> {
            if(imieTF.isEmpty()){
                Notification.show("Wpisz imię", 2000, Notification.Position.MIDDLE);
            }
            else if(nazwiskoTF.isEmpty()){
                Notification.show("Wpisz nazwisko", 2000, Notification.Position.MIDDLE);
            }
            else if(nrklasySelect.isEmpty()){
                Notification.show("Wpisz nazwisko", 2000, Notification.Position.MIDDLE);
            }
            else {
                try {
                   var parent= repo.getByUczenId(fp.findStudPar(imieTF.getValue(),nazwiskoTF.getValue(),nrklasySelect.getValue()));
                    K k = new K(parent.get().getNrTelefonu(), parent.get().getEmail());
                    grid.setItems(k);
                }
                catch (Exception expe){
                    Notification blad = new Notification("Bład"+ expe.getMessage());
                    blad.setDuration(2500);
                    blad.setPosition(Notification.Position.MIDDLE);
                    blad.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    blad.open();
                }
            }
        });


        add(new H1("Dane kontaktowe opiekunów uczniów"), hl,znajdz,grid);
    }
}
