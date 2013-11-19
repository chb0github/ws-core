package org.bongiorno.common.utils.functions;

import org.bongiorno.common.utils.Function;
import org.bongiorno.common.utils.WSCollections;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CsvToList extends PropertyEditorSupport implements Function<String, List<String>> {

    public List<String> apply(String input) {
        return transform(input);
    }

    public String getAsText() {
        Object value = getValue();
        if (!(value instanceof Collection))
            throw new IllegalArgumentException("Only a Collection may be used. Type was: " + value.getClass());

        return WSCollections.delimitedCollection((Collection) value, ",").toString();
    }

    public void setAsText(String text) throws IllegalArgumentException {
        setValue(transform(text));
    }

    public static List<String> transform(String input) {
        List<String> retVal = new ArrayList<String>();

        if ((input != null) && (input.trim().length() > 0)) {
            String[] values = input.split(",");
            for (String value : values)
                retVal.add(value.trim());
        }
        return retVal;
    }
}
