package com.yf.afreesvg.filter;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.util.DoubleConverter;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SVGColorFilterTest {
    private SVGCanvas canvas;
    private Document document;
    private SVGColorFilter filter;


    @Before
    public void setUp() throws Exception {
        filter = new SVGColorFilter(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = null;

        builder = factory.newDocumentBuilder();
        document = builder.newDocument();
        canvas = mock(SVGCanvas.class);
        DoubleConverter doubleConverter = new DoubleConverter(1);
        when(canvas.getGeomDoubleConverter()).thenReturn(doubleConverter);
    }

    @Test
    public void convertToSVGElement() {
        Element element = filter.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertNotNull(element.getFirstChild());
        assertEquals(0, Float.valueOf(element.getAttribute("width")), 0.00001);
        assertEquals(0, Float.valueOf(element.getAttribute("height")), 0.00001);
        assertEquals(0, Float.valueOf(element.getAttribute("x")), 0.00001);
        assertEquals(0, Float.valueOf(element.getAttribute("y")), 0.00001);
    }

    @Test
    public void getColorMatrix() {
        assertArrayEquals(new float[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, filter.getColorMatrix(), 0.000001f);
    }

    @Test
    public void setColorMatrix() {
        filter.setColorMatrix(new float[]{1, 2, 3});
        assertArrayEquals(new float[]{1, 2, 3}, filter.getColorMatrix(), 0.000001f);

    }

    @Test
    public void getType() {
        assertEquals(SVGColorFilter.SVGColorFilterEffect.TYPE_MATRIX, filter.getType());
    }

    @Test
    public void setType() {
        filter.setType(SVGColorFilter.SVGColorFilterEffect.TYPE_HUEROTATE);
        assertEquals(SVGColorFilter.SVGColorFilterEffect.TYPE_HUEROTATE, filter.getType());
    }
}