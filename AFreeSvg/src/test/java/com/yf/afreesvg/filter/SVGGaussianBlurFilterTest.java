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

public class SVGGaussianBlurFilterTest {
    private SVGCanvas canvas;
    private Document document;
    private SVGGaussianBlurFilter filter;
    private SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect effect;

    @Before
    public void setUp() throws Exception {
        filter = new SVGGaussianBlurFilter(0.5f, 0.5f);
        effect = new SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect(0.5f, 0.5f);
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
        assertEquals("filter", element.getTagName());
        assertNotNull(element.getFirstChild());
        assertEquals(0, Float.valueOf(element.getAttribute("width")), 0.00001);
        assertEquals(0, Float.valueOf(element.getAttribute("height")), 0.00001);
        assertEquals(0, Float.valueOf(element.getAttribute("x")), 0.00001);
        assertEquals(0, Float.valueOf(element.getAttribute("y")), 0.00001);

    }

    @Test
    public void effectConvertToSVGElement() {
        effect.setIn("in");
        effect.setResult("result");
        Element element = effect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);

        assertEquals("feGaussianBlur", element.getTagName());

        assertEquals("" + canvas.getGeomDoubleConverter().apply(0.5f) +
                        "," + canvas.getGeomDoubleConverter().apply(0.5f)
                , element.getAttribute("stdDeviation"));
        assertEquals("in", element.getAttribute("in"));
        assertEquals("result", element.getAttribute("result"));

    }


    @Test
    public void getStdDeviationX() {
        filter.setStdDeviationX(5f);
        assertEquals(5, filter.getStdDeviationX(), 0.00001);
    }

    @Test
    public void setStdDeviationX() {
        filter.setStdDeviationX(10f);
        assertEquals(10, filter.getStdDeviationX(), 0.00001);
    }

    @Test
    public void getStdDeviationY() {
        filter.setStdDeviationY(5f);
        assertEquals(5, filter.getStdDeviationY(), 0.00001);
    }

    @Test
    public void setStdDeviationY() {
        filter.setStdDeviationY(10f);
        assertEquals(10, filter.getStdDeviationY(), 0.00001);
    }
}