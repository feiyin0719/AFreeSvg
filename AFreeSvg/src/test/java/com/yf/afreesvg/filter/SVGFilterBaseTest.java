package com.yf.afreesvg.filter;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.TestConstant;
import com.yf.afreesvg.util.DoubleConverter;

import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SVGFilterBaseTest {

    protected SVGCanvas canvas;
    protected Document document;


    @Before
    public void setUp() throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = null;

        builder = factory.newDocumentBuilder();
        document = builder.newDocument();
        canvas = mock(SVGCanvas.class);
        DoubleConverter doubleConverter = new DoubleConverter(1);
        when(canvas.getGeomDoubleConverter()).thenReturn(doubleConverter);
    }

    public void filterBaseTest(Element element) {
        assertNotNull(element);
        assertNotNull(element.getFirstChild());
        assertEquals(0, Float.valueOf(element.getAttribute("width")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("height")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("x")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("y")), TestConstant.DELTA_F);
    }

    public void effectBaseTest(SVGBaseFilter.SVGBaseFilterEffect effect) {
        effect.setIn("in");
        effect.setResult("result");
        Element element = effect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertEquals("in", element.getAttribute("in"));
        assertEquals("result", element.getAttribute("result"));

    }
}
