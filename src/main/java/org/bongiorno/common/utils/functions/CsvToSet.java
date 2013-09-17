package org.bongiorno.common.utils.functions;

import org.bongiorno.common.utils.Function;
import org.bongiorno.common.utils.VdcCollections;

import java.beans.PropertyEditorSupport;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CsvToSet extends PropertyEditorSupport implements Function<String, Set<String>> {

    public Set<String> apply(String input) {
		Set<String> retVal = new HashSet<String>();

		if ((input != null) && (input.trim().length() > 0)) {
            String[] values = input.split(",");
            for (String value : values)
                retVal.add(value.trim());
        }
        return retVal;
    }

    public String getAsText() {
        Object value = getValue();
        if (!(value instanceof Collection))
            throw new IllegalArgumentException("Only a Collection may be used. Type was: " + value.getClass());

        return VdcCollections.delimitedCollection((Collection) value, ",").toString();
    }

    public void setAsText(String text) throws IllegalArgumentException {
        setValue(apply(text));
    }
}
