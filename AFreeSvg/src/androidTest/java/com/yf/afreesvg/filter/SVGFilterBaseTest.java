package com.yf.afreesvg.filter;

import com.yf.afreesvg.SVGBaseTest;
import com.yf.afreesvg.TestConstant;

import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SVGFilterBaseTest extends SVGBaseTest {


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
