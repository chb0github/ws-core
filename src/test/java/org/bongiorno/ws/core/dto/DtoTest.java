package org.bongiorno.ws.core.dto;

import org.junit.Test;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DtoTest {

    @Test
    public void testStylize() throws Exception {
        CheckStyle test = new CheckStyle();
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<checkStyle>\n" +
                "   <firstName>Christian</firstName>\n" +
                "   <lastName>Bongiorno</lastName>\n" +
                "   <age>10</age>\n" +
                "</checkStyle>";
        String result = test.stylize(this.getClass().getResourceAsStream("/identity.xslt"));
        assertEquals(expected,result);
    }

    @Test
    public void testToJson() throws Exception {
        CheckStyle test = new CheckStyle();
        String expected = "{\"firstName\":\"Christian\",\"lastName\":\"Bongiorno\",\"age\":10}";
        String result = test.toJson(false);
        assertEquals(expected,result);
    }

    @Test
    public void testToXml() throws Exception {
        CheckStyle test = new CheckStyle();
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><checkStyle><firstName>Christian</firstName><lastName>Bongiorno</lastName><age>10</age></checkStyle>";
        String result = test.toXml(false);
        assertEquals(expected,result);
    }

    @XmlRootElement
    private static class CheckStyle extends AbstractDto {
        private String firstName = "Christian";
        private String lastName = "Bongiorno";
        private Integer age = 10;

        private String nullValue = null;
    }
}