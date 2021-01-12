package pl.school.apkaszkolna.guis.common;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.textfield.TextField;

public class PeselField extends CustomField <String> {
    private final TextField peselfield = new TextField();

    public PeselField() {
        peselfield.setLabel("PESEL");
        peselfield.setPattern("[0-9]*");
        peselfield.setPreventInvalidInput(true);
        peselfield.setMaxLength(11);
        peselfield.setWidthFull();
        add(peselfield);
    }

    @Override
    protected String generateModelValue() {
        return peselfield.getValue();
    }

    @Override
    protected void setPresentationValue(String newPresentationValue) {
        if (newPresentationValue == null) {
            peselfield.setValue(null);
        }

    }
}
