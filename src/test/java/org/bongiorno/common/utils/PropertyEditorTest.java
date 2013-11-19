package org.bongiorno.common.utils;

import org.junit.Test;

import java.beans.PropertyEditor;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class PropertyEditorTest {


    @Test
    @SuppressWarnings("unchecked")
    public void testSetPropertyEditor() throws Exception {
        PropertyEditor editor = new StringSetPropertyEditor();
        String expected = "christian,bongiorno";

        String noSpace = "christian,bongiorno,bongiorno";
        editor.setAsText(noSpace);
        Set<String> results = (Set<String>) editor.getValue();

        assertEquals(2,results.size());
        assertTrue(results.contains("christian"));
        assertTrue(results.contains("bongiorno"));

        assertEquals(expected, editor.getAsText());

        String withSpace = "christian, bongiorno";
        editor.setAsText(withSpace);

        assertEquals(2,results.size());
        results = (Set<String>) editor.getValue();
        assertTrue(results.contains("christian"));
        assertTrue(results.contains("bongiorno"));

        // though we set WITH space, the editor strips spaces around tokens. This verifies that
        assertEquals(expected, editor.getAsText());

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListPropertyEditor() throws Exception {
        PropertyEditor editor = new StringListPropertyEditor();

        String noSpace = "christian,bongiorno,bongiorno";
        editor.setAsText(noSpace);
        List<String> results = (List<String>) editor.getValue();

        assertEquals(3,results.size());
        assertTrue(results.contains("christian"));
        assertTrue(results.contains("bongiorno"));
        assertTrue(results.contains("bongiorno"));

        assertEquals(noSpace, editor.getAsText());

        String withSpace = "christian, bongiorno , bongiorno";
        editor.setAsText(withSpace);
        results = (List<String>) editor.getValue();

        assertEquals(3,results.size());
        assertEquals("christian", results.get(0));
        assertEquals("bongiorno", results.get(1));
        assertEquals("bongiorno", results.get(2));


        // though we set WITH space, the editor strips spaces around tokens. This verifies that
        assertEquals(noSpace, editor.getAsText());

    }
}
