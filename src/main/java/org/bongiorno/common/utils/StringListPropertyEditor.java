package org.bongiorno.common.utils;

/**
 * @author cbongiorno
 *         Date: 4/21/12
 *         Time: 4:52 PM
 */

import java.beans.PropertyEditorSupport;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class StringListPropertyEditor extends PropertyEditorSupport {

    public String getAsText() {
        Object value = getValue();
        if (!(value instanceof List))
            throw new IllegalArgumentException("Only a List of Strings may be used. Type was: " + value.getClass());

        return VdcCollections.delimitedCollection((Collection<String>) value, ",").toString();
    }

    public void setAsText(String text) throws IllegalArgumentException {
        List<String> retVal = new LinkedList<String>();

		if ((text != null) && (text.trim().length() > 0)) {
            String[] values = text.split(",");
            for (String value : values)
                retVal.add(value.trim());
        }

        setValue(retVal);
    }
}
