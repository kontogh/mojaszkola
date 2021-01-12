package pl.school.apkaszkolna.guis.admionowe;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.annotation.UIScope;
import pl.school.apkaszkolna.entities.Comments;
import pl.school.apkaszkolna.repos.KomentRepo;

import java.util.List;

@UIScope
@PageTitle("Komentarze")
public class Komentarze extends VerticalLayout {
    private KomentRepo repo;

    public Komentarze(KomentRepo repo) {
        this.repo = repo;
        Grid<Comments> grid = new Grid<>(Comments.class);
        grid.removeAllColumns();
        grid.addColumn(Comments::getStworzony).setHeader("Powstał dnia").setWidth("90px");
        grid.addColumn(Comments::getTresc).setHeader("Treść");
        List<Comments> lista = (List<Comments>) repo.findAll();
        grid.setItems(lista);
        if(lista!= null){
            add(new H1("Komentarze użykowników"), grid);
        }
        else {
            add(new H1("Komentarze użykowników"), new H3("Brak komentarzy"));
        }
    }
}
