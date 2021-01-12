package pl.school.apkaszkolna.guis.admionowe;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.PlanLekcji;
import pl.school.apkaszkolna.guis.komponenty.Dni;
import pl.school.apkaszkolna.guis.komponenty.Przedmioty;
import pl.school.apkaszkolna.repos.PersonelRepo;
import pl.school.apkaszkolna.repos.PlanLekcjiRepo;
import pl.school.apkaszkolna.services.FillPersonel;

@UIScope
@PageTitle("Plany Lekcji")
public class StworzPlanLekcji extends VerticalLayout {

    private PersonelRepo repo;
    private FillPersonel fp;
    private PlanLekcjiRepo repoPL;

    public StworzPlanLekcji(PersonelRepo repo, FillPersonel fp, PlanLekcjiRepo repoPL) {
        this.repo = repo;
        this.fp = fp;
        this.repoPL = repoPL;

        Div klasatekst = new Div();
        Div dzienTxt = new Div();
        Div przedmiotTxt = new Div();
        Div lekcjaTxt = new Div();

        Select dzien = new Select();
        dzien.setLabel("Dzień tygodnia");
        dzien.setItems(Dni.values());
        dzien.addValueChangeListener(ev->dzienTxt.setText(ev.getValue().toString()));

        Select<String> nrklasySelect = new Select();
        nrklasySelect.setLabel("Wybierz klasę");
        nrklasySelect.setItems("1a", "1b", "2a", "2b", "3a", "3b");
        nrklasySelect.addValueChangeListener(ee-> klasatekst.setText(ee.getValue()));

        HorizontalLayout hl = new HorizontalLayout(nrklasySelect, dzien);

        Select<String> przedmiotSelect = new Select<>();
        przedmiotSelect.setLabel("Przedmioty");
        przedmiotSelect.setItems(fp.listaprzedmiow());
        przedmiotSelect.addValueChangeListener(e->przedmiotTxt.setText(e.getValue()));//todo tutaj

        Select<String> lekcja = new Select();
        lekcja.setLabel("Godzina lekcyjna");
        lekcja.setItems("08:00-08:45", "08:50-09:35", "09:40-10:25","10:50-11:30", "11:35-12:20","12:30-13:15",
                "13:20-14:05", "14:10-14:55","15:00-15:45");
        lekcja.addValueChangeListener(e->lekcjaTxt.setText(e.getValue()));

        Grid<PlanLekcji>  grid = new Grid<>(PlanLekcji.class);
        grid.removeAllColumns();
        Grid.Column<PlanLekcji> przedmiotColumn = grid.addColumn(PlanLekcji::getPrzedmiot).setHeader("Przedmiot");
        Grid.Column<PlanLekcji> lekcjaColumn = grid.addColumn(PlanLekcji::gethOd).setHeader("Godzina");
        Grid.Column salaColumn = grid.addColumn(PlanLekcji::getSalaPLid).setHeader("Numer sali");
        grid.addColumn(PlanLekcji::getNauczycielPl).setHeader("Nauczyciel");

        Button zapisz = new Button("Zatwierdź");
        zapisz.addClickListener(event -> {
                if(klasatekst.getText().isEmpty()){
                    Notification.show("Podaj klasę", 2000, Notification.Position.MIDDLE);
                }
                else if (przedmiotTxt.getText().isEmpty()){
                    Notification.show("Podaj przedmiot", 2000, Notification.Position.MIDDLE);
                }
                else if(lekcjaTxt.getText().isEmpty()){
                    Notification.show("Podaj lekcję", 2000, Notification.Position.MIDDLE);
                }
                else if(dzienTxt.getText().isEmpty()){
                    Notification.show("Podaj dzień", 2000, Notification.Position.MIDDLE);
                }
                else{
                    try {
                    fp.zapiszPL(klasatekst.getText(), przedmiotTxt.getText(),lekcjaTxt.getText(), dzienTxt.getText() );
                    Notification.show("Zapisano", 2000, Notification.Position.MIDDLE);
                    grid.setItems(fp.planByKlasa(klasatekst.getText(), dzienTxt.getText()));
                }
                    catch (Exception e){
                        Notification.show("Błąd"+e.getMessage(), 2000, Notification.Position.MIDDLE);
                    }
            }

        });

        HorizontalLayout hl2 = new HorizontalLayout(przedmiotSelect, lekcja, zapisz);



        Binder<PlanLekcji>  binderPL = new Binder<>(PlanLekcji.class);
        Editor<PlanLekcji> editor = grid.getEditor();

        binderPL.bind(przedmiotSelect, "przedmiot");
        przedmiotColumn.setEditorComponent(przedmiotSelect);

        przedmiotSelect.getElement().addEventListener("keydown", event -> {
        }).setFilter("event.key === 'Tab' && !event.shiftKey");


        Button dodaj = new Button("Stwórz");
        Button fill = new Button("f");
        fill.addClickListener(r->{
            fp.wypelenij();
        });

        add(new H1("Tworzenie planu lekcji"), hl, hl2, grid, fill);
    }
}
