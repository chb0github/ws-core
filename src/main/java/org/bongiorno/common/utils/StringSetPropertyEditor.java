package org.bongiorno.common.utils;

import org.bongiorno.common.utils.functions.CsvToSet;

import java.beans.PropertyEditorSupport;
import java.util.Collection;
import java.util.Set;

public class StringSetPropertyEditor extends PropertyEditorSupport {

    private CsvToSet csvToSet = new CsvToSet();

	public String getAsText() {
        Object value = getValue();
        if (!(value instanceof Set))
            throw new IllegalArgumentException("Only a Set of Strings may be used. Type was: " + value.getClass());

        return WSCollections.delimitedCollection((Collection<String>) value, ",").toString();
	}

	public void setAsText(String text) throws IllegalArgumentException {
		setValue(csvToSet.apply(text));
	}
}
