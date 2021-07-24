package com.yf.afreesvg.filter;

import com.yf.afreesvg.SVGCanvas;
import com.yf.afreesvg.TestConstant;
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

public class SVGGaussianBlurFilterTest extends SVGFilterBaseTest {


    @Test
    public void convertToSVGElement() {
        SVGGaussianBlurFilter filter = new SVGGaussianBlurFilter(0.5f, 0.5f);
        Element element = filter.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertEquals("filter", element.getTagName());
        assertNotNull(element.getFirstChild());
        assertEquals(0, Float.valueOf(element.getAttribute("width")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("height")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("x")), TestConstant.DELTA_F);
        assertEquals(0, Float.valueOf(element.getAttribute("y")), TestConstant.DELTA_F);

    }

    @Test
    public void effectConvertToSVGElement() {
        SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect effect = new SVGGaussianBlurFilter.SVGGaussianBlurFilterEffect(0.5f, 0.5f);

        Element element = effect.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);

        assertEquals("feGaussianBlur", element.getTagName());

        assertEquals("" + canvas.getGeomDoubleConverter().apply(0.5f) +
                        "," + canvas.getGeomDoubleConverter().apply(0.5f)
                , element.getAttribute("stdDeviation"));
        effectBaseTest(effect);

    }


    @Test
    public void getStdDeviationX() {
        SVGGaussianBlurFilter filter = new SVGGaussianBlurFilter(0.5f, 0.5f);

        filter.setStdDeviationX(5f);
        assertEquals(5, filter.getStdDeviationX(), TestConstant.DELTA_F);
    }

    @Test
    public void setStdDeviationX() {
        SVGGaussianBlurFilter filter = new SVGGaussianBlurFilter(0.5f, 0.5f);

        filter.setStdDeviationX(10f);
        assertEquals(10, filter.getStdDeviationX(), TestConstant.DELTA_F);
    }

    @Test
    public void getStdDeviationY() {
        SVGGaussianBlurFilter filter = new SVGGaussianBlurFilter(0.5f, 0.5f);

        filter.setStdDeviationY(5f);
        assertEquals(5, filter.getStdDeviationY(), TestConstant.DELTA_F);
    }

    @Test
    public void setStdDeviationY() {
        SVGGaussianBlurFilter filter = new SVGGaussianBlurFilter(0.5f, 0.5f);

        filter.setStdDeviationY(10f);
        assertEquals(10, filter.getStdDeviationY(), TestConstant.DELTA_F);
    }
}