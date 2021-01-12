package pl.school.apkaszkolna.guis.common;


import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import pl.school.apkaszkolna.entities.WydarzeniaPozalekcyne;
import pl.school.apkaszkolna.repos.WydarzeniaRepo;

import java.util.Collections;
import java.util.List;

@PageTitle("Home")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class HomeView extends Div implements AfterNavigationObserver {

    Grid<WydarzeniaPozalekcyne> grid = new Grid<>();
    @Autowired
    private WydarzeniaRepo wydarzenia;

    public HomeView() {
        setId("home-view");
        addClassName("home-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(person -> createCard(person));
        add(grid);
    }

    private HorizontalLayout createCard(WydarzeniaPozalekcyne person) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");


        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Image image = new Image();
        image.setSrc(person.getImage());
        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        Span name = new Span("Data dodania wydarzenia: ");
        name.addClassName("name");
        Span date = new Span(person.getDataDodania());
        date.addClassName("date");
        header.add(name, date);

        Span post = new Span(person.getOpis());
        post.addClassName("post");
        //cut off section


        description.add(header, post/*, actions*/);
        card.add(image, description);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        // Set some data when this view is displayed.
        List<WydarzeniaPozalekcyne> wydarzenia = (List<WydarzeniaPozalekcyne>) this.wydarzenia.findAll();
        Collections.reverse(wydarzenia);

        grid.setItems(wydarzenia);
    }
    //section 2
}