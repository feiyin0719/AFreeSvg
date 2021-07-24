package com.yf.afreesvg.gradient;

import com.yf.afreesvg.TestConstant;

import org.junit.Test;
import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SVGRadialGradientTest extends SVGGradientBaseTest {

    @Test
    public void convertToSVGElement() {
        SVGRadialGradient radialGradient = new SVGRadialGradient();
        assertEquals(0f, radialGradient.getCx(), TestConstant.DELTA_F);
        assertEquals(0f, radialGradient.getCy(), TestConstant.DELTA_F);
        assertEquals(0f, radialGradient.getR(), TestConstant.DELTA_F);
        assertEquals(0f, radialGradient.getFx(), TestConstant.DELTA_F);
        assertEquals(0f, radialGradient.getFy(), TestConstant.DELTA_F);
        assertEquals(0f, radialGradient.getFr(), TestConstant.DELTA_F);

        radialGradient.setCx(1f);
        radialGradient.setCy(2f);
        radialGradient.setR(3f);
        radialGradient.setFx(4f);
        radialGradient.setFy(5f);
        radialGradient.setFr(6f);
        assertEquals(1f, radialGradient.getCx(), TestConstant.DELTA_F);
        assertEquals(2f, radialGradient.getCy(), TestConstant.DELTA_F);
        assertEquals(3f, radialGradient.getR(), TestConstant.DELTA_F);
        assertEquals(4f, radialGradient.getFx(), TestConstant.DELTA_F);
        assertEquals(5f, radialGradient.getFy(), TestConstant.DELTA_F);
        assertEquals(6f, radialGradient.getFr(), TestConstant.DELTA_F);
        Element element = radialGradient.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertNotNull(element);
        assertEquals("radialGradient", element.getTagName());
        assertEquals(1f, Float.valueOf(element.getAttribute("cx")), TestConstant.DELTA_F);
        assertEquals(2f, Float.valueOf(element.getAttribute("cy")), TestConstant.DELTA_F);
        assertEquals(3f, Float.valueOf(element.getAttribute("r")), TestConstant.DELTA_F);
        assertEquals(4f, Float.valueOf(element.getAttribute("fx")), TestConstant.DELTA_F);
        assertEquals(5f, Float.valueOf(element.getAttribute("fy")), TestConstant.DELTA_F);
        assertEquals(6f, Float.valueOf(element.getAttribute("fr")), TestConstant.DELTA_F);

        baseGradientTest(radialGradient);
    }
}