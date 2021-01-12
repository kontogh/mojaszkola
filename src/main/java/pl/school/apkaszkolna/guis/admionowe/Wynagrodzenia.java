package pl.school.apkaszkolna.guis.admionowe;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Personel;
import pl.school.apkaszkolna.repos.PersonelRepo;
import pl.school.apkaszkolna.services.FillPersonel;

import java.util.List;

@UIScope
@PageTitle("Pensje personelu")
public class Wynagrodzenia extends VerticalLayout {
    private PersonelRepo repo;
    private FillPersonel fp;

    public Wynagrodzenia(PersonelRepo repo, FillPersonel fp) {
        this.repo = repo;
        this.fp = fp;

        Grid<Personel> grid = new Grid<>(Personel.class);
        Binder<Personel> binder = new Binder<>(Personel.class);
        Editor<Personel> editor = grid.getEditor();
        editor.setBinder(binder);

        List <Personel> list = (List<Personel>) repo.findAll();
        grid.removeAllColumns();
        grid.addColumn(Personel::getImie).setHeader("Imię");
        grid.addColumn(Personel::getNazwisko).setHeader("Nazwisko");
        grid.addColumn(Personel::gethPracy).setHeader("Przepracowano");
        Grid.Column<Personel> stawkaColumn = grid.addColumn(Personel::getPensja).setHeader("Stawka");


        NumberField pensjaTF = new NumberField();
        stawkaColumn.setEditorComponent(item -> {
            binder.bind(pensjaTF, "pensja");
            return pensjaTF; });

        pensjaTF.getElement()
                .addEventListener("keydown",
                        event -> grid.getEditor().cancel())
                .setFilter("event.key === 'Tab' && !event.shiftKey");

        grid.addItemDoubleClickListener(event -> {
            grid.getEditor().editItem(event.getItem());
            pensjaTF.focus();
        });

        binder.addValueChangeListener(event -> {
            grid.getEditor().refresh();
        });

        grid.getEditor().addCloseListener(event -> {
            if (binder.getBean() != null) {
                fp.resetEarnigs(binder.getBean(), binder.getBean().getPensja() );
            }});

        grid.addColumn(Personel::getPrzedmiot).setHeader("Przedmiot");
        grid.setItems(list);
        add(new H1("Wynagrodzenia personelu"), grid);
    }
}
